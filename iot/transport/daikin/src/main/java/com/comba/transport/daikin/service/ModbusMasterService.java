package com.comba.transport.daikin.service;

import com.alibaba.fastjson.JSONObject;
import com.comba.transport.daikin.common.ByteUtil;
import com.comba.transport.daikin.common.Constant;
import com.comba.transport.daikin.message.ModbusAbstractMessage;
import com.comba.transport.daikin.message.PresetSingleRegisterMessage;
import com.comba.transport.daikin.message.ReadInputRegisterMessage;
import com.comba.transport.daikin.message.ReadInputRegisterRespMessage;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author maozhihui
 * @Description:MODUBS 模块服务启动类
 * @create 2017/12/13 13:02
 **/
@Slf4j
@Service
public class ModbusMasterService {
	
	@Value("${daikin.enabled}")
	private boolean enabled;
    @Value("${daikin.tcpserver.ip}")
    private String ip;
    @Value("${daikin.tcpserver.port}")
    private int port;
    @Value("${daikin.device.address}")
    private String address;
    @Value("${daikin.interval}")
    private int interval;
	@Value("${daikin.online.timeout}")
	private int onlineTimeout;
    
	@Autowired
	private DAIKINDeviceApiService deviceApiService;
    private static String sessionId = UUID.randomUUID().toString();
    private static DAIKINDeviceApiService deviceService;
    private static ModbusMaster master;
    private ScheduledExecutorService scheduledThreadPool;
    private String[] hardIdentitys;
    
    @PostConstruct
    public void init(){
		if (enabled){
			//初始化 modbus master，启动监听服务
			initMaster();
			//空调设备注册
			initDeviceRegister();
		}else {
			log.warn("modbus module is disabled.");
		}
    }

    private void initMaster(){
		IpParameters parameters = new IpParameters();
		parameters.setHost(ip);
		parameters.setPort(port);
		parameters.setEncapsulated(true);
		master = new ModbusFactory().createTcpListener(parameters);
		try {
			master.init();
		} catch (ModbusInitException e) {
			log.error("modbus init failed,cause [{}]",e.getMessage());
		}
    }

    private void initDeviceRegister(){
    	deviceService = deviceApiService;
    	hardIdentitys = address.split(",");
    	scheduledThreadPool = Executors.newScheduledThreadPool(1);
		scheduledThreadPool.scheduleAtFixedRate(new DeviceRegisterTask(master,hardIdentitys,onlineTimeout),
				0,interval, TimeUnit.MILLISECONDS);
    }

    //查询状态
    public static Object checkSlaveStatus(byte slaveAddr,short startAddr,short num){
    	//编码
    	byte[] codeBytes = encodeReadInputRegisterMessage(slaveAddr,startAddr,num);
    	//发送命令
    	return messageSent(codeBytes);
    }
    
    //slaveAddr:从机地址
    //startAddr:42001,即40001+2000。startAddr=2000起始地址是对室内机控制的
    //state:0-停止，1-运转
    public static void setAirConditioningState(byte slaveId,int groupId,int No,byte state){
    	
    	//获取内机的运转/停止
    	//1-00  0x07d0(2000+30001=32001)
    	//1-01  0x07d6(2006+30001=32007)
    	//内机起始地址偏移量 0x07d0
    	short startAddr = 0x07d0;
    	short readAddr = (short) (startAddr + (groupId - 1)*16 + No*6);
    	
    	short num = 6;
    	ModbusAbstractMessage msg = (ModbusAbstractMessage)checkSlaveStatus(slaveId,readAddr,num);
    	
    	if(msg instanceof ReadInputRegisterRespMessage){
    		ReadInputRegisterRespMessage message = (ReadInputRegisterRespMessage)msg;
    		short data = message.getData()[0];
    		if(data%2 == 0 && state == 0x01){//当前内机的状态是停止的,运转内机
    			data = (short) (data + 1);
    			log.info("打开空调 {}", No);
    		}
    		else if(data%2 != 0 && state == 0x00){//当前内机的状态是运转的，停止内机
    			data = (short) (data - 1);
    			log.info("关闭空调 {}", No);
    		}
    		else{ //其他情况，不操作
    			if(data%2 == 0){
    				log.info("空调{} 当前状态是停止的.",No);
    			}
    			else{
    				log.info("空调{} 当前状态是运转的！",No);
    			}
    			return;
    		}
    		
        	//设置内机的运转/停止
        	//1-00  0x07d0(2000+40001=42001)
        	//1-01  0x07d3(2003+40001=42004)
        	//1-02  0x07d6(2006+40001=42007)
    		short presetAddr = (short) (startAddr + (slaveId - 1)*16 + No*3);
        	//编码
        	byte[] codeBytes = encodePresetSingleRegisterMessage(slaveId, presetAddr, data);
        	log.info("设置空调开关命令：{}",ByteUtil.bytesToHexString(codeBytes));
        	//发送命令
        	Object resultMsg = messageSent(codeBytes);
        	String hardIdentity = "" + slaveId +"-"+ groupId +"-"+ ByteUtil.byteToHexString((byte) No);
        	
        	if(resultMsg != null){
        		uploadAirConditioningState(hardIdentity,state);
        	}
        	else{
        		log.error("状态设置失败，空调 {}",No);
        	}
		}
    }
    
    //设置空调模式 model:0-送风，1-暖房，2-冷风
    public static void setAirConditioningModel(byte slaveId,int groupId,int No,byte model){
    	
    	//获取内机的运转/停止
    	//1-00  0x07d0(2000+30001=32001)
    	//1-01  0x07d6(2006+30001=32007)
    	//内机起始地址偏移量 0x07d0
    	short startAddr = 0x07d0;
    	short readAddr = (short) (startAddr + (groupId - 1)*16 + No*6);
    	
    	short num = 6;
    	ModbusAbstractMessage msg = (ModbusAbstractMessage)checkSlaveStatus(slaveId,readAddr,num);
    	
    	if(msg instanceof ReadInputRegisterRespMessage){
    		ReadInputRegisterRespMessage message = (ReadInputRegisterRespMessage)msg;
    		//空调状态
    		short stateData = message.getData()[0];
    		//空调运转模式
    		short modelData = message.getData()[1];
 
			if(stateData%2 == 0){
				log.warn("空调已关闭.请先打开空调！");
				return;
			}
			
			if(model == 0x00){ //送风
				modelData = (short) ((modelData & 0xFF00) + 0x00);
			}
			else if(model == 0x01){//暖气
				modelData = (short) ((modelData & 0xFF00) + 0x01);
			}
			else if(model == 0x02){  //制冷
				modelData = (short) ((modelData & 0xFF00) + 0x02);
			}
			else{
				log.error("不支持设置模式：{}",model);
				return;
			}
			
        	//设置空调的运转模式
        	//1-00  0x07d1(2001+40001=42002)
        	//1-01  0x07d4(2004+40001=42005)
        	//1-02  0x07d7(2007+40001=42008)
    		short presetAddr = (short) (startAddr + 1 + (slaveId - 1)*16 + No*3);
        	//编码
        	byte[] codeBytes = encodePresetSingleRegisterMessage(slaveId, presetAddr, modelData);
        	log.info("设置空调模式命令：{}",ByteUtil.bytesToHexString(codeBytes));
        	//发送命令
        	Object resultMsg = messageSent(codeBytes);
        	String hardIdentity = "" + slaveId +"-"+ groupId +"-"+ ByteUtil.byteToHexString((byte) No);
        	if(resultMsg != null){
        		uploadAirConditioningModel(hardIdentity,(byte) model);
        	}
        	else{
        		log.error("模式设置失败，空调 {}",No);
        	}
		}
    }
    
    //设置空调温度
    public static void setAirConditioningTemperature(byte slaveId,int groupId,int No,short temperature){
    	
    	//获取内机的运转/停止
    	//1-00  0x07d0(2000+30001=32001)
    	//1-01  0x07d6(2006+30001=32007)
    	//内机起始地址偏移量 0x07d0
    	short startAddr = 0x07d0;
    	short readAddr = (short) (startAddr + (groupId - 1)*16 + No*6);
    	
    	short num = 6;
    	ModbusAbstractMessage msg = (ModbusAbstractMessage)checkSlaveStatus(slaveId,readAddr,num);
    	
    	if(msg instanceof ReadInputRegisterRespMessage){
    		ReadInputRegisterRespMessage message = (ReadInputRegisterRespMessage)msg;
    		//空调状态
    		short stateData = message.getData()[0];
    		//空调状态
    		short modelData = message.getData()[1];
    		//空调温度
    		short tempData = message.getData()[2];
 
			if(stateData%2 == 0){
				log.warn("空调已关闭.请先打开空调！");
				return;
			}
			short model = (short) (modelData & 0x00FF);

    		if(model!= 0x02){//制冷
    			log.warn("请先设置到空调制冷模式，再设置空调温度！");
    			return;
    		}

			log.debug("当前空调温度：{}'C",tempData/10);
			log.debug("设置空调温度：{}'C",temperature);
    		
        	//设置空调的温度
        	//1-00  0x07d2(2002+40001=42003)
        	//1-01  0x07d5(2005+40001=42006)
        	//1-02  0x07d8(2008+40001=42009)
    		short presetAddr = (short) (startAddr + 2 + (slaveId - 1)*16 + No*3);
        	//编码
        	byte[] codeBytes = encodePresetSingleRegisterMessage(slaveId, presetAddr, (short) (temperature*10));
        	log.info("设置空调温度命令：{}",ByteUtil.bytesToHexString(codeBytes));
        	//发送命令
        	Object resultMsg = messageSent(codeBytes);
        	String hardIdentity = "" + slaveId +"-"+ groupId +"-"+ ByteUtil.byteToHexString((byte) No);
        	if(resultMsg != null){
        		uploadAirConditioningTemperature(hardIdentity,temperature);
        	}
        	else{
        		log.error("温度设置失败，空调 {}",No);
        	}
		}
    }
    
    //ReadInputRegisterMessage消息编码
    public static byte[] encodeReadInputRegisterMessage(byte slaveId,short offset,short num){
    	ReadInputRegisterMessage readMsg = new ReadInputRegisterMessage();
    	readMsg.setSlaveId(slaveId);
    	readMsg.setOffset(offset);
    	readMsg.setRegisterNum(num);

    	byte[] bytes = readMsg.encodeMsg();
    	log.debug("msg:[{}],encodeMsg:[{}]",readMsg,bytes);
    	return bytes;
    }
    
    //PresetSingleRegisterMessage消息编码
    public static byte[] encodePresetSingleRegisterMessage(byte slaveId,short offset,short data){
    	PresetSingleRegisterMessage msg = new PresetSingleRegisterMessage();
    	msg.setSlaveId(slaveId);
    	msg.setOffset(offset);
    	msg.setData(data);
    	
    	byte[] bytes = msg.encodeMsg();
    	log.debug("msg:[{}],encodeMsg:[{}]",msg,bytes);
    	return bytes;
    }
    
    //接收消息
    public static Object messageReceived(byte[] message){

    	ModbusAbstractMessage msg = null;
		if(message[1] == Constant.READ_INPUT_REGISTER){
			msg = new ReadInputRegisterRespMessage();
		}else if(message[1] == Constant.PRESET_SINGLE_REGISTER){
			msg = new PresetSingleRegisterMessage();
		}
		else {
			log.error("Can not decode the message buffer![{}]",ByteUtil.bytesToHexString(message));
			return null;
		}
		
		msg.decodeMsg(message);
		log.debug("{}",msg);

		return msg;
    }
    
    //发送消息
    public static Object messageSent(byte[] bytes){
    	Object obj = null;
		try {
			ByteQueue reqByte = new ByteQueue(bytes);
			ModbusRequest request = ModbusRequest.createModbusRequest(reqByte);
			ModbusResponse response = master.send(request);
			ByteQueue result = new ByteQueue(30);
			Thread.sleep(5+20);
			response.write(result);

			obj = messageReceived(result.popAll());
			Thread.sleep(5+20);
		} 
		catch (ModbusTransportException e) {
			log.error("slavedId [{}] send message failed,cause [{}]",e.getSlaveId(),e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("{}",e.getMessage());
		}

		return obj;
    }
    
  	//设备注册
  	public static void onDeviceRegister(String hardIdentity,long onlineTimeout){
  		//String hardIdentity = "" + slaveId + groupId + No;
		//根据硬件标识找到设备
		String s[] = hardIdentity.split("-");
		byte slaveId = (byte)Integer.parseInt(s[0]);
        int groupId = Integer.parseInt(s[1]);
        int No = Integer.parseInt(s[2]);
        
    	short startAddr = 0x07d0;
    	short readAddr = (short) (startAddr + (groupId - 1)*16 + No*6);
    	short num = 6;
    	byte state = 0;
    	short model = 0;
    	short temperature = 0;
    	ModbusAbstractMessage msg = (ModbusAbstractMessage)checkSlaveStatus(slaveId,readAddr,num);
    	
    	if(msg instanceof ReadInputRegisterRespMessage){
    		ReadInputRegisterRespMessage message = (ReadInputRegisterRespMessage)msg;
    		
    		//空调状态
    		short stateData = message.getData()[0];
    		if(stateData%2 == 0){//当前内机的状态是停止的
    			state = 0;
    			log.debug("注册设备，[{}]空调已关闭.",hardIdentity);
    		}
    		else if(stateData%2 != 0){//当前内机的状态是运转的
    			state = 1;
    			log.debug("注册设备，[{}]空调已打开！",hardIdentity);
    		}
    		//空调模式
    		short modelData = message.getData()[1];
    		model = (short) (modelData & 0x0F);
    		log.debug("注册设备，[{}]空调模式{}'C！",hardIdentity,model);
    		//空调温度
    		short temperatureData = message.getData()[2];
    		temperature = (short) (temperatureData/10);
    		log.debug("注册设备，[{}]空调温度{}'C！",hardIdentity,temperature);
    	}
    	
  		if(deviceService != null)
  		{
  			JSONObject jsonObj = new JSONObject(true);
  			jsonObj.put("switch", state);
  			jsonObj.put("onlineTimeout", onlineTimeout);
  			deviceService.register(null,sessionId,hardIdentity, jsonObj.toString());
  			//上报属性参数
  			JSONObject attributeObj = new JSONObject(true);
  			attributeObj.put("switch", state);
  			attributeObj.put("model", model);
  			attributeObj.put("temperature", temperature);
  			deviceService.updateDeviceAttributes(null, sessionId, hardIdentity, attributeObj.toString());
  		}
  	}
  	
  	//上报空调的状态
  	public static void uploadAirConditioningState(String hardIdentity,byte state){

  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("switch", state);
  		
  		if(deviceService == null)
  			return;
  		log.debug("上报遥测:{}",jsonObj.toString());
		deviceService.uploadTelemetry(null,sessionId,hardIdentity,jsonObj.toString());
  	}
  	
  	//上报空调的模式
  	public static void uploadAirConditioningModel(String hardIdentity,byte model){

  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("model", model);
  		
  		if(deviceService == null)
  			return;
  		log.debug("上报遥测:{}",jsonObj.toString());
		deviceService.uploadTelemetry(null,sessionId,hardIdentity,jsonObj.toString());
  	}
  	
  	//上报空调的温度
  	public static void uploadAirConditioningTemperature(String hardIdentity,short temperature){

  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("temperature", temperature);
  		
  		if(deviceService == null)
  			return;
  		log.debug("上报遥测:{}",jsonObj.toString());
		deviceService.uploadTelemetry(null,sessionId,hardIdentity,jsonObj.toString());
  	}
  	
  	//更新空调的状态
  	public static void updataAirConditioningState(String hardIdentity,byte state){

  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("switch", state);
  		
  		if(deviceService == null)
  			return;
  		log.debug("更新空调开关状态:{}",jsonObj.toString());
		deviceService.updateDeviceAttributes(null,sessionId,hardIdentity,jsonObj.toString());
  	}
  	
    @PreDestroy
    public void destroy(){
        // TODO 断开连接，销毁资源
        if (master != null){
            master.destroy();
            scheduledThreadPool.shutdown();
            deRegister(address);
        }
    }
    
    public static void deRegister(String address){
    	if(address != null){
			String addrs[] = address.split(",");
			for(int i = 0; i < addrs.length; i++){	
				String hardIdentity = addrs[i];
				JSONObject jsonObj = new JSONObject(true);
				jsonObj.put("gatewayid", "device_id");
				deviceService.deregister(null,sessionId,hardIdentity,jsonObj.toString());
			}
		}
    }
}

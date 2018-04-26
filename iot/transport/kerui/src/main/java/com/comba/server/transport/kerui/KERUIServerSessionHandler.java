package com.comba.server.transport.kerui;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSONObject;
import com.comba.server.transport.kerui.bean.ControllerInfo;
import com.comba.server.transport.kerui.bean.DeviceInfo;
import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;
import com.comba.server.transport.kerui.message.KERUIAbstractMessage;
import com.comba.server.transport.kerui.message.KERUIBindingScreenAndDetectorRespMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdReqMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeRespMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberReqMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorBoardParameterRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorDisplayModelRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorParameterRespMessage;
import com.comba.server.transport.kerui.message.KERUISetTimeRespMessage;
import com.comba.server.transport.kerui.service.KERUIDeviceApiService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * KERUI Server Handler
 * 
 * @author huangjinlong
 * 
 */
@Slf4j
public class KERUIServerSessionHandler extends IoHandlerAdapter {
	
	private KERUIDeviceApiService deviceApiService = null;
	//没有想pshare模块这样使用sessionMap方式保存会话信息，是因为整个通信只有一个，就是与主控通信
	//若有多个主控，则设备的命名规则需要改变，并且主控制器要有硬件标识。
	public static IoSession ioSession = null;
	//控制器下的设备
	public static ConcurrentHashMap<Integer,ControllerInfo> mastersMap = new ConcurrentHashMap<Integer,ControllerInfo>();
    //与主控一个设备通信,建立一个session,所以sessionId创建一个全局的即可。
	private static String sessionId = UUID.randomUUID().toString();
	private long onlineTimeout = 0;
	
	public KERUIServerSessionHandler(KERUIDeviceApiService service,long timeout){
		deviceApiService = service;
		onlineTimeout = timeout;
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Exception caught,{}",cause.getMessage());
	}

	@Override
  	public void messageReceived(IoSession session, Object obj) throws Exception {
		log.debug("Message received from client{}:{}",session.getRemoteAddress(), obj);		
		KERUIAbstractMessage message = (KERUIAbstractMessage)obj;
		ControllerInfo controllerInfo = mastersMap.get(message.getSrcAddr());//控制器地址
		
		if(message instanceof KERUIMasterNumberRespMessage) {//节点控制器的数量和地址
			KERUIMasterNumberRespMessage msg = (KERUIMasterNumberRespMessage)message;
			for(int i = 0; i < msg.getMastersNum(); i++){
				byte nodeAddr = msg.getMastersAddr()[i];
				controllerInfo = mastersMap.get(nodeAddr);
				//缓存控制节点信息
				if(controllerInfo == null){
					controllerInfo = new ControllerInfo();
					controllerInfo.setAddress(nodeAddr);
					mastersMap.put((int) nodeAddr, controllerInfo);
				}
			}
		}
		else if(message instanceof KERUIDestMasterDevicesRespMessage){//目标节点控制器下的设备
			KERUIDestMasterDevicesRespMessage msg = (KERUIDestMasterDevicesRespMessage)message;
			if(controllerInfo != null){
				//一个控制器其中1至4路是有效的，
				for(int groupId = 0; groupId < 4; groupId++){
					BitMap group = msg.getGroup()[groupId];
					int groupLen = group.getBitMap().length;
					int byteLen = 8;//一个字节有8位，代表8个地址
					//1路最多有4*8=32个探测器
					for(int index = 0; index < groupLen*byteLen; index++){
						//设备硬件标识：控制器地址-组-编号
						int address = 32*groupId + index;
						byte node = controllerInfo.getAddress();
						String hardIdentity = node + "-" + (groupId+1) + "-" + index;
						DeviceInfo device = controllerInfo.getDevicesMap().get(hardIdentity);
						if(device == null){
							device = new DeviceInfo();
							device.setRegistered(false);
						}

						if(group.getBit(index) == 1 && !device.isRegistered()){//设备存在,且设备未注册
							device.setControllerAddr(node);    //控制器地址
							device.setDeviceAddr(ByteUtil.intToByte(address));//设备地址
							device.setSn(hardIdentity);		   //设备SN
							device.setType(Constant.DETECTOR);			//初始化为保留
							device.setState(Constant.NOT_CAR_STATE);	//初始化为无车
							onDeviceRegister(session,hardIdentity);
							controllerInfo.getDevicesMap().put(hardIdentity, device);
							log.debug("[{}]设备注册", hardIdentity);
						}
					}
				}
				//保存节点控制器下所有的设备信息
				mastersMap.put((int) controllerInfo.getAddress(), controllerInfo);
			}	
		}
		else if(message instanceof KERUIDestMasterDevicesTypeRespMessage){//查询节点控制器下设备的类型
			KERUIDestMasterDevicesTypeRespMessage msg = (KERUIDestMasterDevicesTypeRespMessage)message;
			if(controllerInfo != null){
				for(Map.Entry<String, DeviceInfo> entry:controllerInfo.getDevicesMap().entrySet()){
					byte deviceAddr = ByteUtil.hexStringTobyte(entry.getKey());
					DeviceInfo device = entry.getValue();
					String hardIdentity = device.getSn();
					byte type = msg.getDeviceTypeByAddr(deviceAddr);
					device.setType(type);
					controllerInfo.getDevicesMap().put(hardIdentity, device);
					//设备类型参数不上报
					//onUploadDeviceType(session,deviceInfo.getSn(),type);
				}
				//更新设备的设备类型
				mastersMap.put((int) controllerInfo.getAddress(), controllerInfo);
			}
		}
		else if(message instanceof KERUIDestMasterDetectorsStateRespMessage){//查询节点控制器下设备的状态
			KERUIDestMasterDetectorsStateRespMessage msg = (KERUIDestMasterDetectorsStateRespMessage)message;
			if(controllerInfo != null){
				for(Map.Entry<String, DeviceInfo> entry:controllerInfo.getDevicesMap().entrySet()){
					byte deviceAddr = ByteUtil.hexStringTobyte(entry.getKey());
					DeviceInfo device = entry.getValue();
					String hardIdentity = device.getSn();
					
					//设备类型是探测器，且设备已注册
					if(device.getType() == Constant.DETECTOR && device.isRegistered()){
						byte state = msg.getDetectorStateByAddr(deviceAddr);
						device.setState(state);
						controllerInfo.getDevicesMap().put(hardIdentity, device);
						onUploadDeviceState(session,hardIdentity,state);
					}
				}
				//更新设备的探测器的设备状态，有车，无车
				mastersMap.put((int) controllerInfo.getAddress(), controllerInfo);	
			}
		}
		else if(message instanceof KERUIDetectorStateRespMessage){//	下面的消息暂不处理
		}
		else if(message instanceof KERUISetDetectorDisplayModelRespMessage){//
		}
		else if(message instanceof KERUISetTimeRespMessage){//
		}
		else if(message instanceof KERUISetDetectorBoardParameterRespMessage){//
		}
		else if(message instanceof KERUIBindingScreenAndDetectorRespMessage){//
		}
		else if(message instanceof KERUISetDetectorParameterRespMessage){//
		}
		else if(message instanceof KERUICpuIdRespMessage){//
		}
		else {
			log.error("Can not decode message:",message);	
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("Send message to client:{}[{}]", session.getRemoteAddress(), message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.warn("Session closed {}！",session.getRemoteAddress());
		try {
			for(Map.Entry<Integer, ControllerInfo> entry:mastersMap.entrySet()){
				ControllerInfo controllerInfo = entry.getValue();
				if(controllerInfo != null){
					for(Map.Entry<String, DeviceInfo> device:controllerInfo.getDevicesMap().entrySet()){
						String hardIdentity = device.getValue().getSn();
						JSONObject jsonObj = new JSONObject(true);
						//优化去注册流程
			  			jsonObj.put("gatewayid", "device_id");
						deviceApiService.deRegister(session,sessionId,hardIdentity,jsonObj.toString());
					}
				}
			}
			deviceApiService.closeSession(session, sessionId);
			session.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug("Session created {}！",session.getRemoteAddress());
	}

  	@Override
  	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
  		log.debug("sessionIdle!");
		//获取主控制器，地址0x01
		byte masterAddr = 0x01;
		getMasterNumberReqMessage(session,masterAddr);
		
/* 		byte masterAddr = 0x01;
		getMasterNumberReqMessage(session,masterAddr);*/
/*		byte nodeAddr = 0x03;
		getDestMasterDevicesReqMessage(session,nodeAddr);*/
  		//byte nodeAddr = 0x03;
		//getDestMasterDevicesReqMessage(session,nodeAddr);
		//控制节点的设备的类型
  		//byte nodeAddr = 0x03;
	  	//getDestMasterDevicesTypeReqMessage(session,nodeAddr);
		/*for(Map.Entry<String, ControllerInfo> entry:mastersMap.entrySet()){
			byte nodeAddr = ByteUtil.hexStringTobyte(entry.getKey());
			if(nodeAddr == 0x01)	//主控制器，不连接设备
				continue ;
			getDestMasterDevicesReqMessage(session,(byte) 0x04);
			Thread.sleep(3000);
		}*/
  	}

  	@Override
  	public void sessionOpened(IoSession session) throws Exception {
  		log.info("Session opened {}！",session.getRemoteAddress());
		try {
			//获取主控制器，地址0x01
			byte masterAddr = 0x01;
			getMasterNumberReqMessage(session,masterAddr);
			ioSession = session;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
  	}
  	
  	//获取控制器地址
  	public static void getMasterNumberReqMessage(IoSession session, byte masterAddr){
		KERUIMasterNumberReqMessage msg = new KERUIMasterNumberReqMessage();
		msg.setDestAddr(masterAddr);//主控制器地址0x01
		msg.setSrcAddr(Constant.PC_ADDR);
		msg.setReserve((byte) 0x00);
		session.write(msg);
  	}
  	
  	//获取目标控制节点的设备
  	public static void getDestMasterDevicesReqMessage(IoSession session,byte nodeAddr){
		KERUIDestMasterDevicesReqMessage msg = new KERUIDestMasterDevicesReqMessage();
		msg.setDestAddr(nodeAddr);//控制节点地址0x03
		msg.setSrcAddr(Constant.PC_ADDR);
		session.write(msg);
  	}
  	
  	//控制节点的设备的类型
  	public static void getDestMasterDevicesTypeReqMessage(IoSession session,byte nodeAddr){
  		KERUIDestMasterDevicesTypeReqMessage msg = new KERUIDestMasterDevicesTypeReqMessage();
		msg.setDestAddr(nodeAddr);//控制节点地址0x03
		msg.setSrcAddr(Constant.PC_ADDR);
		session.write(msg);
  	}
  	
  	//控制节点的探测器状态
  	public static void getDestMasterDetectorsStateReqMessage(IoSession session,byte nodeAddr){
  		KERUIDestMasterDetectorsStateReqMessage msg = new KERUIDestMasterDetectorsStateReqMessage();
		msg.setDestAddr(nodeAddr);//控制节点地址0x03
		msg.setSrcAddr(Constant.PC_ADDR);
		session.write(msg);
  	}
  	
	//7F 00 03 00 05 00 01 41 DA 5F (3-1) 0x41->65 = 32 + 32 + 1
	//7F 00 03 00 05 00 01 42 9A 5E (3-2) 0x42->66 = 32 + 32 + 2
	//7F 00 03 00 05 00 02 03 42 9E 0A 
  	//获取探测器状态
  	private void getDetectorStateReqMessage(IoSession session,byte nodeAddr,byte deviceAddr){
  		KERUIDetectorStateReqMessage msg = new KERUIDetectorStateReqMessage();
		msg.setDestAddr(nodeAddr);
		msg.setSrcAddr(Constant.PC_ADDR);
		//msg.setMasterAddr((byte) 0x03);//这项,在协议文档上有，但实际协议版本（经过测试）没有
		msg.setDetectorAddr(deviceAddr);//怎么表示地3组的0x01地址。 0x41->65 = 32 + 32 + 1
		session.write(msg);
  	}
  	
  	//获取CPU ID
  	private void getCpuIdReqMessage(IoSession session,byte nodeAddr){
  		KERUICpuIdReqMessage msg = new KERUICpuIdReqMessage();
		msg.setDestAddr(nodeAddr);
		msg.setSrcAddr(Constant.PC_ADDR);
		session.write(msg);
  	}
  	
  	//注册
  	public void onDeviceRegister(IoSession session,String sn) {
		JSONObject jsonObj = new JSONObject(true);
		jsonObj.put("onlineTimeout", onlineTimeout);
		log.debug("{},{}",sn,jsonObj.toString());
  		if(deviceApiService != null){
  			deviceApiService.subDeviceRegister(session,sessionId,sn,jsonObj.toString());
  		}
  	}
  	//更新设备类型
  	public void onUploadDeviceType(IoSession session,String sn, byte type) {
		JSONObject jsonObj = new JSONObject(true);
		if(type == Constant.DETECTOR)//探测器
			jsonObj.put("device.type", "探测器");
		else if(type == Constant.SCREEN)//屏
			jsonObj.put("device.type", "屏");
		else if(type == Constant.WIRELESS_LAMP)//无线灯
			jsonObj.put("device.type", "无线灯");
		else if(type == Constant.RESERVE)//保留
			jsonObj.put("device.type", "保留");
		else
			return;
		log.debug("{},{}",sn,jsonObj.toString());
		
  		if(deviceApiService != null){
  			deviceApiService.updateDeviceAttributes(session,sessionId,sn,jsonObj.toString());
  		}
  	}
  	
  	//更新探测器状态
  	public void onUploadDeviceState(IoSession session,String sn, byte state) {
		JSONObject jsonObj = new JSONObject(true);
		if(state == Constant.NOT_CAR_STATE)
			jsonObj.put("isAvailable", "true");
		else if(state == Constant.HAS_CAR_STATE)
			jsonObj.put("isAvailable", "false");
		else if(state == Constant.ERROR_STATE)
			jsonObj.put("isAvailable", "error");
		else
			return;
		log.debug("{},{}",sn,jsonObj.toString());
		
  		if(deviceApiService != null){
  			deviceApiService.uploadTelemetry(session,sessionId,sn,jsonObj.toString());
  		}
  	}
}

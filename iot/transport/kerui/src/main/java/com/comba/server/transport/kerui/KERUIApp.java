package com.comba.server.transport.kerui;

import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.comba.server.transport.kerui.common.BitMap;
import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;
import com.comba.server.transport.kerui.common.ModbusCrc16;
import com.comba.server.transport.kerui.message.KERUIAbstractMessage;
import com.comba.server.transport.kerui.message.KERUIBindingScreenAndDetectorReqMessage;
import com.comba.server.transport.kerui.message.KERUIBindingScreenAndDetectorRespMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdReqMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesReqMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberReqMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorDisplayModelReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorDisplayModelRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorParameterReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorParameterRespMessage;
import com.comba.server.transport.kerui.message.KERUISetTimeReqMessage;
import com.comba.server.transport.kerui.message.KERUISetTimeRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorBoardParameterReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorBoardParameterRespMessage;
import com.comba.server.transport.kerui.service.KERUIDeviceApiService;
/**
 * Hello world!
 *
 */
@Slf4j
@Service
public class KERUIApp 
{
	@Autowired
	private KERUIDeviceApiService deviceApiService;
	
	@Value("${kerui.enabled}")
	private boolean enabled;
    @Value("${kerui.tcpserver.ip}")
    private String ip;
    @Value("${kerui.tcpserver.port}")
    private int port;
	@Value("${kerui.interval}")
	private int interval;
	@Value("${kerui.online.timeout}")
	private int onlineTimeout;
	@PostConstruct
    public void init()
    {
        log.info( "KERUIApp init !" );
    	if (enabled){
    		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
    		new KERUIServer(config,deviceApiService).start();
    	}
        //
        //String sn = "2-1-1";
        
/*        onUploadDeviceType(sn,Constant.DETECTOR);
        onUploadDeviceType(sn,Constant.SCREEN);
        onUploadDeviceType(sn,Constant.WIRELESS_LAMP);
        onUploadDeviceType(sn,Constant.RESERVE);*/
       // onUploadDeviceType(sn,Constant.DETECTOR);

/*        onUploadDeviceState(sn,Constant.NOT_CAR_STATE);
        onUploadDeviceState(sn,Constant.HAS_CAR_STATE);
        onUploadDeviceState(sn,Constant.ERROR_STATE);*/
        //onUploadDeviceState(sn,Constant.HAS_CAR_STATE);
    }
	
  	//更新设备类型(设备参数)
  	public void onUploadDeviceType(String sn, byte type) {
  		String sessionId = UUID.randomUUID().toString();
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
		deviceApiService.updateDeviceAttributes(null,sessionId,sn, jsonObj.toString());
  	}
  	
  	//更新探测器状态(遥测属性)
  	public void onUploadDeviceState(String sn, byte state) {
  		String sessionId = UUID.randomUUID().toString();
		JSONObject jsonObj = new JSONObject(true);
		if(state == Constant.NOT_CAR_STATE)
			jsonObj.put("isAvailable", 0);
		else if(state == Constant.HAS_CAR_STATE)
			jsonObj.put("isAvailable", 1);
		else if(state == Constant.ERROR_STATE)
			jsonObj.put("isAvailable", 2);
		else
			return;
		deviceApiService.uploadTelemetry(null,sessionId,sn, jsonObj.toString());
  	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		
/*		MasterNumberReq();
		MasterNumberResp();
		
		KERUIDestMasterDevicesReqMessage();
		KERUIDestMasterDevicesRespMessage();
		
		KERUIDestMasterDevicesTypeReqMessage();
		KERUIDestMasterDevicesTypeRespMessage();
		
		KERUIDestMasterDetectorsStateReqMessage();
		KERUIDestMasterDetectorsStateRespMessage();
		
		KERUIDetectorStateReqMessage();
		KERUIDetectorStateRespMessage();
		
		KERUISetDetectorDisplayModelReqMessage();
		KERUISetDetectorDisplayModelRespMessage();
		
		KERUISetTimeReqMessage();
		KERUISetTimeRespMessage();
		
		KERUISetDetectorBoardParameterReqMessage();
		KERUISetDetectorBoardParameterRespMessage();
		
		KERUIBindingScreenAndDetectorReqMessage();
		KERUIBindingScreenAndDetectorRespMessage();
		
		KERUISetDetectorParameterReqMessage();
		KERUISetDetectorParameterRespMessage();
		
		KERUICpuIdReqMessage();
		KERUICpuIdRespMessage();*/
		
/*		byte[] dataBytes = {0x7F,0x00,0x00,0x03,(byte) 0x85,0x00,0x01,0x01,(byte) 0xB6,0x5C}; 
		
		IoBuffer buf=IoBuffer.allocate(dataBytes.length).setAutoExpand(true);
		buf.put(dataBytes);
		buf.flip();
		
		decode(buf);*/
		
/*		byte[] bytes = ByteUtil.byteTo8Byte((byte)0xff);
		System.out.println(ByteUtil.toHexForLog(bytes));
		
		byte b = ByteUtil.binaryStringToByte("11010011");
		System.out.println(ByteUtil.byteToInt(b));
		
		byte[] bs = {(byte) 0x10,(byte) 0x01};
		byte[] bsTo8Bs = ByteUtil.bytesToBitBytes(bs);
		System.out.println(ByteUtil.toHexForLog(bsTo8Bs));*/
		
	
		//byte[] arr_buff = {0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00} ;//kerui
		//byte[] arr_buff = {0x02, 0x06, 0x0B, 0x00, 0x00, 0x01};//
		byte[] arr_buff = {0x02, 0x03, 0x00, 0x00, 0x00, 0xA, (byte) 0xC5};
		//byte[] arr_buff = {(byte) 0xFA, 0x03, 0x00, 0x00, 0x00, 0x01};// 91 81//大金空调
 		//byte[] arr_buff = {0x00,(byte) 0xFF,0x01,0x04,0x01,0x02};
		//byte[] arr_buff = {0x7F,0x00,0x00,0x00,0x01,0x00,0x01,0x00};
		//byte[] arr_buff = {0x7F,0x00,0x00,0x01,(byte) 0x81,0x00,0x03,0x03,0x06,0x08};
		
		byte[] crcBytes = new byte[2]; 
		ByteUtil.shortToByte_LH((short) ModbusCrc16.getCrc16(arr_buff), crcBytes, 0);
		System.out.println(ByteUtil.bytesToHexString(crcBytes));
		
/*		byte[] buff = {(byte) 0x84,0x19,0x00,0x00};
		BitMap bitmap = new BitMap(buff);
		//bitmap.setBit(3);
		//bitmap.setBit(5);
		//bitmap.setBit(8);
		//bitmap.setBitMap(15);
		for(int i=0;i<buff.length*8;i++){
			System.out.print(bitmap.getBit(i)+" ");
		}*/
		
	}
	
	public static void MasterNumberResp() {

		KERUIMasterNumberRespMessage msg = new KERUIMasterNumberRespMessage();
		msg.setDestAddr(Constant.PC_ADDR);
		msg.setSrcAddr((byte) 0x01);
		//msg.setCrc16((byte) 0x11);
		
		//
		byte[] mastersAddr = {0x03,0x06,0x08};
		msg.setMastersAddr(mastersAddr);
		msg.setMastersNum((short) mastersAddr.length);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void MasterNumberReq() {

		KERUIMasterNumberReqMessage msg = new KERUIMasterNumberReqMessage();
		msg.setDestAddr(Constant.PC_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);//最后计算crc
		
		//
		msg.setReserve((byte) 0x00);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDevicesReqMessage() {
		KERUIDestMasterDevicesReqMessage msg = new KERUIDestMasterDevicesReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDevicesRespMessage() {
		KERUIDestMasterDevicesRespMessage msg = new KERUIDestMasterDevicesRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		byte[] devices = {0x0E,0x00,0x00,0x10};
		BitMap bMap = new BitMap(devices);
		//msg.setDevices(bMap);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDevicesTypeRespMessage() {
		KERUIDestMasterDevicesTypeRespMessage msg = new KERUIDestMasterDevicesTypeRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDevicesTypeReqMessage() {
		KERUIDestMasterDevicesTypeReqMessage msg = new KERUIDestMasterDevicesTypeReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDetectorsStateReqMessage() {
		KERUIDestMasterDetectorsStateReqMessage msg = new KERUIDestMasterDetectorsStateReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDestMasterDetectorsStateRespMessage() {
		KERUIDestMasterDetectorsStateRespMessage msg = new KERUIDestMasterDetectorsStateRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDetectorStateReqMessage() {
		KERUIDetectorStateReqMessage msg = new KERUIDetectorStateReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIDetectorStateRespMessage() {
		KERUIDetectorStateRespMessage msg = new KERUIDetectorStateRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetDetectorDisplayModelReqMessage() {
		KERUISetDetectorDisplayModelReqMessage msg = new KERUISetDetectorDisplayModelReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetDetectorDisplayModelRespMessage() {
		KERUISetDetectorDisplayModelRespMessage msg = new KERUISetDetectorDisplayModelRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetTimeReqMessage() {
		KERUISetTimeReqMessage msg = new KERUISetTimeReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		msg.setYear((short) 2017);
		msg.setMonth((byte) 12);
		msg.setDay((byte) 4);
		msg.setHour((byte) 16);
		msg.setMinute((byte) 12);
		msg.setSecond((byte) 0);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetTimeRespMessage() {
		KERUISetTimeRespMessage msg = new KERUISetTimeRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		msg.setYear((short) 2017);
		msg.setMonth((byte) 12);
		msg.setDay((byte) 4);
		msg.setHour((byte) 16);
		msg.setMinute((byte) 12);
		msg.setSecond((byte) 0);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetDetectorBoardParameterReqMessage(){
		KERUISetDetectorBoardParameterReqMessage msg = new KERUISetDetectorBoardParameterReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetDetectorBoardParameterRespMessage(){
		KERUISetDetectorBoardParameterRespMessage msg = new KERUISetDetectorBoardParameterRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUIBindingScreenAndDetectorReqMessage(){
		KERUIBindingScreenAndDetectorReqMessage msg = new KERUIBindingScreenAndDetectorReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	public static void KERUIBindingScreenAndDetectorRespMessage(){
		KERUIBindingScreenAndDetectorRespMessage msg = new KERUIBindingScreenAndDetectorRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static void KERUISetDetectorParameterReqMessage(){
		KERUISetDetectorParameterReqMessage msg = new KERUISetDetectorParameterReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	public static void KERUICpuIdReqMessage(){
		KERUICpuIdReqMessage msg = new KERUICpuIdReqMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	public static void KERUICpuIdRespMessage(){
		KERUICpuIdRespMessage msg = new KERUICpuIdRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	public static void KERUISetDetectorParameterRespMessage(){
		KERUISetDetectorParameterRespMessage msg = new KERUISetDetectorParameterRespMessage();
		msg.setDestAddr(Constant.BROADCAST_ADDR);
		msg.setSrcAddr((byte) 0x00);
		//msg.setCrc16((short) 0x16);
		
		IoBuffer buf = encode(msg);
		decode(buf);
	}
	
	public static IoBuffer encode(Object message) {

		IoBuffer buf=IoBuffer.allocate(2048).setAutoExpand(true);
		KERUIAbstractMessage msg = (KERUIAbstractMessage)message;
		
		msg.encodeData(null);//不做编码工作，参数没用，函数名称改一下(Data赋值)
		msg.encodeMessage(buf);//整个消息体编码
		
		buf.flip();
		log.info("encode message:" + ByteUtil.toHexForLog(buf));
		return buf;
	}
	
	public static void decode(IoBuffer in) {
		
		KERUIAbstractMessage absMsg = new KERUIAbstractMessage();
		absMsg.decodeMessage(in);
		
		byte cmdCode = absMsg.getCmdCode();
		byte[] data = absMsg.getData();
		
		KERUIAbstractMessage msg = null;
		if(cmdCode == Constant.MASTER_NUMBER_RESP) {
			msg = new KERUIMasterNumberRespMessage();
		}
		else if(cmdCode == Constant.MASTER_NUMBER_REQ){//
			msg = new KERUIMasterNumberReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_RESP){//
			msg = new KERUIDestMasterDevicesRespMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_REQ){//
			msg = new KERUIDestMasterDevicesReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_REQ){//
			msg = new KERUIDestMasterDevicesTypeReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_RESP){//
			msg = new KERUIDestMasterDevicesTypeRespMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_REQ){//
			msg = new KERUIDestMasterDetectorsStateReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_RESP){//
			msg = new KERUIDestMasterDetectorsStateRespMessage();
		}
		else if(cmdCode == Constant.DETECTOR_STATE_REQ){//
			msg = new KERUIDetectorStateReqMessage();
		}
		else if(cmdCode == Constant.DETECTOR_STATE_RESP){//
			msg = new KERUIDetectorStateRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_REQ){//
			msg = new KERUISetDetectorDisplayModelReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_RESP){//
			msg = new KERUISetDetectorDisplayModelRespMessage();
		}
		else if(cmdCode == Constant.SET_TIME_REQ){//
			msg = new KERUISetTimeReqMessage();
		}
		else if(cmdCode == Constant.SET_TIME_RESP){//
			msg = new KERUISetTimeRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_REQ){//
			msg = new KERUISetDetectorBoardParameterReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_RESP){//
			msg = new KERUISetDetectorBoardParameterRespMessage();
		}
		else if(cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_REQ){//
			msg = new KERUIBindingScreenAndDetectorReqMessage();
		}
		else if(cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_RESP){//
			msg = new KERUIBindingScreenAndDetectorRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_PARAMETER_REQ){//
			msg = new KERUISetDetectorParameterReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_PARAMETER_RESP){//
			msg = new KERUISetDetectorParameterRespMessage();
		}
		else if(cmdCode == Constant.CPU_ID_REQ){//
			msg = new KERUICpuIdReqMessage();
		}
		else if(cmdCode == Constant.CPU_ID_RESP){//
			msg = new KERUICpuIdRespMessage();
		}
		else {
			log.info("can't decode cmdCode:{}",cmdCode);
		}
		msg.initMessage(absMsg);//absMsg赋值->msg
		msg.decodeData(null);//data可不要
			
		log.info(msg.toString());
		
	}
}

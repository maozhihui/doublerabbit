package com.comba.transport.pshare;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.alibaba.fastjson.JSONObject;
import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.message.PSHAREAbstractMessage;
import com.comba.transport.pshare.message.PSHAREDeviceMacMessage;
import com.comba.transport.pshare.message.PSHAREGetLockStateReqMessage;
import com.comba.transport.pshare.message.PSHAREGetLockStateRespMessage;
import com.comba.transport.pshare.message.PSHARELockDownReqMessage;
import com.comba.transport.pshare.message.PSHARELockDownRespMessage;
import com.comba.transport.pshare.message.PSHARELockUpReqMessage;
import com.comba.transport.pshare.message.PSHARELockUpRespMessage;
import com.comba.transport.pshare.service.PSHAREDeviceApiService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Server Handler
 * 
 * @author huangjinlong
 * 
 */
@Slf4j
public class PSHAREServerSessionHandler extends IoHandlerAdapter {

	private PSHAREDeviceApiService deviceApiService = null;
	private DeviceService deviceService = null;
	public static ConcurrentHashMap<IoSession,ConnectionSession> connSessionMap = new ConcurrentHashMap<IoSession,ConnectionSession>();
	long onlineTimeout = 0;
	public PSHAREServerSessionHandler(PSHAREDeviceApiService apiService,DeviceService service,long timeout){
		deviceApiService = apiService;
		deviceService = service;
		onlineTimeout = timeout;
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Exception caught,{}",cause.getMessage());
		session.close();
	}

	@Override
  	public void messageReceived(IoSession session, Object obj) throws Exception {
		log.debug("Message received from client{}:{}",session.getRemoteAddress(), obj);	
		PSHAREAbstractMessage message = (PSHAREAbstractMessage)obj;
		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			log.warn("Can not found the session [{}]",session);
			return;
		}
		String hardIdentity = (connSession.getMac() + "-" + message.getAddr()).toUpperCase();
		
		if(message instanceof PSHAREGetLockStateRespMessage) {//锁状态
			PSHAREGetLockStateRespMessage msg = (PSHAREGetLockStateRespMessage)message;
			connSession.getDevicesMap().put(hardIdentity, msg);
			//若设备已注册，才可以上报遥测。
			boolean isRegistered = connSession.getRegisteredDevices().contains(hardIdentity);
			if(isRegistered){
				onUploadTelemetry(connSession,hardIdentity,msg);
			}
			else{
				onDeviceRegister(connSession,hardIdentity,msg);
			}
		}
		else if(message instanceof PSHARELockUpRespMessage){
			PSHARELockUpRespMessage msg = (PSHARELockUpRespMessage)message;
			if(msg.getHead() == 0x5A){
				byte state = 0;
				onUploadLockState(connSession,hardIdentity,state);
			}
			PSHAREServerSessionHandler.getLockStateReqMessage(session,(byte) msg.getAddr());
		}
		else if(message instanceof PSHARELockDownRespMessage){
			PSHARELockDownRespMessage msg = (PSHARELockDownRespMessage)message;
			if(msg.getHead() == 0x5A){
				byte state = 1;
				onUploadLockState(connSession,hardIdentity,state);
			}
			PSHAREServerSessionHandler.getLockStateReqMessage(session,(byte) msg.getAddr());
		}
		else if(message instanceof PSHAREDeviceMacMessage){
			PSHAREDeviceMacMessage msg = (PSHAREDeviceMacMessage)message;
			onDeviceMacMessage(connSession,msg);
			log.info("Received PSHAREDeviceMacMessage[{}:{}]",session.getRemoteAddress(), obj);	
		}
		else {
			log.error("can't decode message:{}",message);	
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
			ConnectionSession connSession = connSessionMap.get(session);
			String sessionId = connSession.getSessionId();
			
			if(connSession != null && connSession.getRegisteredDevices().size() > 0) {
				for(Map.Entry<String, PSHAREGetLockStateRespMessage> entry : connSession.getDevicesMap().entrySet()){
					String hardIdentity = entry.getKey().toUpperCase();
					JSONObject jsonObj = new JSONObject(true);
		  			//优化这一块流程，去注册流程无需gatewayid和token
					jsonObj.put("gatewayid", "device_id");
					//设备去注册
					deviceApiService.deregister(session, sessionId,hardIdentity,jsonObj.toString());
					log.warn("Device deregister! hardIdentity = {}",hardIdentity);
				}
				//释放上层SessionActor
				deviceApiService.closeSession(session, sessionId);
				log.warn("Release SessionActor! sessionId = {}",sessionId);
			}
			
			connSessionMap.remove(session);
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
  	}

  	@Override
  	public void sessionOpened(IoSession session) throws Exception {
  		log.info("Session opened {}！",session.getRemoteAddress());
		try {
			ConnectionSession connSession = new ConnectionSession(session);
			connSessionMap.put(session,connSession);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
  	}

  	public static void setLockStateReqMessage(IoSession session, byte addr, String attribute,String value){
  		if(attribute.equals("lockState")){
			byte state = (byte)Integer.parseInt(value);
			if(state == 0){
	  			lockUpReqMessage(session,addr);
	  		}
	  		else if(state == 1){
	  			lockDownReqMessage(session,addr);
	  		}
			log.info("Set lock state [addr={},attribute={},state={}]",addr,attribute,state);
  		}
  		else{
  			log.error("[{}]device not suport setting [{}] !",ByteUtil.byteToHexString(addr),attribute);
  		}
  	}
  	
  	//下发获取锁状态指令
  	public static void getLockStateReqMessage(IoSession session, byte addr){
  		PSHAREGetLockStateReqMessage msg = new PSHAREGetLockStateReqMessage();
  		msg.setAddr(addr);
  		session.write(msg);
  	}
  	
  	//下发升锁指令
  	public static void lockUpReqMessage(IoSession session, byte addr){
  		PSHARELockUpReqMessage msg = new PSHARELockUpReqMessage();
  		msg.setAddr(addr);
  		session.write(msg);
  		log.debug("Send lock up command：[{}]",msg.toString());
  	}
  	
  	//下发降锁指令
  	public static void lockDownReqMessage(IoSession session, byte addr){
  		PSHARELockDownReqMessage msg = new PSHARELockDownReqMessage();
  		msg.setAddr(addr);
  		session.write(msg);
  		log.debug("Send lock down command：[{}]",msg.toString());
  	}
  	
  	//MAC消息处理
  	public void onDeviceMacMessage(ConnectionSession connSession, PSHAREDeviceMacMessage msg){
  		IoSession session = connSession.getSession();
  		connSession.setMac(ByteUtil.bytesToHexString(msg.getMac()).toUpperCase());
		//查询适配器下设备列表
		List<String> deviceList = deviceService.findLikeMacByDeviceHardIdentity(connSession.getMac());
		for(int i = 0; i < deviceList.size(); i++){
			//设备信息，根据硬件标识找到设备，获取相关信息
			String hardIdentity = deviceList.get(i);
    		String s[] = hardIdentity.split("-");
    		Integer addr = Integer.parseInt(s[1]);
    		connSession.getAddressList().add(addr);
		}
		//缓存Map
		connSessionMap.put(session,connSession);
		log.debug("connSessionMap put[{},{}]",session,connSession);
  	}
  	
  	//设备注册
  	public void onDeviceRegister(ConnectionSession connSession, String hardIdentity, PSHAREGetLockStateRespMessage m){
  		IoSession session = connSession.getSession();
  		String sessionId = connSession.getSessionId();
		JSONObject jsonObj = new JSONObject(true);
		jsonObj.put("lockState", m.getLock());
		jsonObj.put("onlineTimeout", onlineTimeout);
  		if(deviceApiService != null)
  		{
  			deviceApiService.register(session,sessionId,hardIdentity,jsonObj.toString());//关联网关
  			log.debug("Device register：{} {}",hardIdentity,jsonObj.toString());
  		}
  	}
  	
  	//上报和更新车位锁的遥测数据和控制参数。
  	public void onUploadLockState(ConnectionSession connSession, String hardIdentity, byte state){
  		IoSession session = connSession.getSession();
  		String sessionId = connSession.getSessionId();
  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("lockState", state);
  		
  		if(deviceApiService != null){
			deviceApiService.updateDeviceAttributes(session,sessionId,hardIdentity,jsonObj.toString());
			deviceApiService.uploadTelemetry(session,sessionId,hardIdentity,jsonObj.toString());
			log.debug("Upload device attributes and telemetry lock state：{} {}",hardIdentity,jsonObj.toString());
  		}
  	}
  	
  	//上报车位锁的遥测数据
  	public void onUploadTelemetry(ConnectionSession connSession, String hardIdentity, PSHAREGetLockStateRespMessage msg){
  		IoSession session = connSession.getSession();
  		String sessionId = connSession.getSessionId();
  		JSONObject jsonObj = new JSONObject(true);
  		jsonObj.put("lockState", msg.getLock());
  		jsonObj.put("csb", msg.getCsb());//传感器状态， 01为有车，00为无车02为变化中 03为未知
  		jsonObj.put("bat", msg.getBat());//电池电量，03满格，02中间，01低电 00没电
  		jsonObj.put("beep", msg.getBeep());//蜂鸣器设置，01为打开，00为关闭
  		
  		if(deviceApiService != null){
  			deviceApiService.uploadTelemetry(session,sessionId,hardIdentity,jsonObj.toString());
  			log.debug("Upload telemetry lock state：{} {}",hardIdentity,jsonObj.toString());
  		}
  	}
}

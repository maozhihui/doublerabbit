package com.comba.transport.pm25;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSONObject;
import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.message.PM25AbstractMessage;
import com.comba.transport.pm25.message.PM25ChangeDeviceAddrRespMessage;
import com.comba.transport.pm25.message.PM25DeviceMacMessage;
import com.comba.transport.pm25.message.PM25QueryDeviceAddrReqMessage;
import com.comba.transport.pm25.message.PM25QueryDeviceAddrRespMessage;
import com.comba.transport.pm25.message.PM25ReadDataReqMessage;
import com.comba.transport.pm25.message.PM25ReadDataRespMessage;
import com.comba.transport.pm25.service.PM25DeviceApiService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * PM2.5 Server Handler
 * 
 * @author huangjinlong
 * 
 */
@Slf4j
public class PM25ServerSessionHandler extends IoHandlerAdapter {

	private PM25DeviceApiService deviceApiService = null;
	private DeviceService deviceService = null;
	// Connection server session管理
	public static ConcurrentHashMap<IoSession,ConnectionSession> connSessionMap = new ConcurrentHashMap<IoSession,ConnectionSession>();
	long onlineTimeout = 0;
	public PM25ServerSessionHandler(PM25DeviceApiService apiService,DeviceService service,long timeout){
		deviceApiService = apiService;
		deviceService = service;
		onlineTimeout = timeout;
		
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		log.error("Exception caught{}",cause.getMessage());
		session.close();
	}

	@Override
  	public void messageReceived(IoSession session, Object obj) throws Exception {
		log.debug("Message received from client{}:{}",session.getRemoteAddress(), obj);	
		PM25AbstractMessage message = (PM25AbstractMessage)obj;
		//数据入库
		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			log.warn("[{}] :can not found the session！",session);
			return;
		}
		
		if(message instanceof PM25ReadDataRespMessage) {//获取数据
			PM25ReadDataRespMessage msg = (PM25ReadDataRespMessage)message;
			String hardIdentity = (connSession.getMac() + "-" + msg.getDeviceAddr()).toUpperCase();
			connSession.getDevicesMap().put(hardIdentity, msg);
			boolean isRegistered = connSession.getRegisteredDevices().contains(hardIdentity);
			if(isRegistered){
				//上报遥测
				onUploadTelemetry(session,hardIdentity,msg);
			}
			else{
				//设备注册
				onDeviceRegister(session,hardIdentity,msg);
			}
		}
		else if(message instanceof PM25ChangeDeviceAddrRespMessage){//更改设备地址
		}
		else if(message instanceof PM25DeviceMacMessage){//注册上报Mac
			PM25DeviceMacMessage msg = (PM25DeviceMacMessage)message;
			onDeviceMacMessage(session,msg);
			
			//test 查询地址
			//queryDeviceAddrReqMessage(session);
		}
		else if(message instanceof PM25QueryDeviceAddrRespMessage){//查询地址响应
			//读取数据
			readDataReqMessage(session,message.getDeviceAddr());
		}
		else {
			log.info("Can not decode message:"+message);	
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("Send message to client:{}[{}]", session.getRemoteAddress(), message.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.warn("Session closed {}！",session.getRemoteAddress());
		try {
			ConnectionSession connSession = connSessionMap.get(session);
			
			if(connSession != null && connSession.getRegisteredDevices().size() > 0) {
				//设备去注册
				for(Map.Entry<String, PM25ReadDataRespMessage> entry : connSession.getDevicesMap().entrySet()){
					String hardIdentity = entry.getKey().toUpperCase();
					JSONObject jsonObj = new JSONObject(true);
		  			jsonObj.put("gatewayid", "device_id");
					deviceApiService.deregister(connSession.getSession(),connSession.getSessionId(),hardIdentity,jsonObj.toString());
					log.warn("Device deregister! hardIdentity = {}",hardIdentity);
				}
				//释放Session
				String sessionId = connSession.getSessionId();
				deviceApiService.closeSession(session, sessionId);
				log.warn("Release SessionActor! sessionId = {}",sessionId);
			}
			// session管理
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
			// session上线
			connSessionMap.put(session,connSession);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
  	}

  	public static void readDataReqMessage(IoSession session,byte addr){
  		PM25ReadDataReqMessage msg = new PM25ReadDataReqMessage();
  		msg.setDeviceAddr(addr);
  		session.write(msg);
  	}
  	
  	public static void queryDeviceAddrReqMessage(IoSession session){
  		PM25QueryDeviceAddrReqMessage msg = new PM25QueryDeviceAddrReqMessage();
  		session.write(msg);
  	}
  	
  	//设备注册
  	public void onDeviceRegister(IoSession session,String hardIdentity,PM25ReadDataRespMessage m){
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
		
  		if(connSession.getMac() != null && deviceApiService != null)
  		{
  			JSONObject jsonObj = new JSONObject(true);
  			jsonObj.put("onlineTimeout", onlineTimeout);
  			deviceApiService.register(session,connSession.getSessionId(),hardIdentity, jsonObj.toString());//关联网关
			log.debug("device register [{},{}]",hardIdentity,jsonObj.toString());
  		}
  	}
  	
	//上报气体浓度和电压值
  	public void onUploadTelemetry(IoSession session,String hardIdentity, PM25ReadDataRespMessage msg) {
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
  		
		if(connSession.getMac() != null && deviceApiService != null)
  		{
			JSONObject jsonObj = new JSONObject(true);
			jsonObj.put("PM2.5", msg.getGasConcentration());
			jsonObj.put("voltage", msg.getVoltageValue());
			deviceApiService.uploadTelemetry(session,connSession.getSessionId(),hardIdentity, jsonObj.toString());
			log.debug("Upload telemetry [{},{}]",hardIdentity,jsonObj.toString());
  		}
  	}
  	
  	//MAC消息处理
  	public void onDeviceMacMessage(IoSession session, PM25DeviceMacMessage msg){
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
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
		connSessionMap.put(session,connSession);
  	}
}

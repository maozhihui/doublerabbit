package com.comba.server.transport.feibit.handler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.alibaba.fastjson.JSONObject;
import com.comba.server.transport.feibit.bean.ConnectionSession;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.message.FEIBITDeviceSwitchStateRespMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesRespMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataRespMessage;
import com.comba.server.transport.feibit.message.FEIBITNodeActiveReportingMessage;
import com.comba.server.transport.feibit.message.FEIBITSettingDeviceRespMessage;
import com.comba.server.transport.feibit.protocol.FEIBITProtocolCodecFactory;
import com.comba.server.transport.feibit.service.FEIBITDeviceApiService;

@Slf4j
public class FEIBITClientSessionHandler extends IoHandlerAdapter {
	//网关列表 <session,ConnectionSession>
	public static ConcurrentHashMap<IoSession,ConnectionSession> connSessionMap = new ConcurrentHashMap<IoSession,ConnectionSession>();
	static FEIBITDeviceApiService deviceApiService = null;
	long onlineTimeout = 0;
	public FEIBITClientSessionHandler(FEIBITDeviceApiService service, long onlineTimeout) {	
		deviceApiService = service;
		this.onlineTimeout = onlineTimeout;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		log.error("Exception caught,{}",cause.getMessage());
		session.close();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		log.debug("Message received from server{}:{}",session.getRemoteAddress(), message);	
		String hardIdentity = null;
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			log.warn("[{}] :can not found the session！",session);
			return ;
		}
		
		if(message instanceof FEIBITGetDevicesRespMessage){			//网关当前连接设备列表
			FEIBITGetDevicesRespMessage msg = (FEIBITGetDevicesRespMessage)message;
			//缓存设备列表信息,shortAddr+endpoint做唯一标识（多路开关，是用endpoint区分）
			hardIdentity = (ByteUtil.shortToHexString(msg.getShortAddr()) + ByteUtil.byteToHexString(msg.getEndpoint())).toUpperCase();
			connSession.getDevicesMap().put(hardIdentity, msg);
			onDeviceRegister(connSession,hardIdentity);
		}
		else if(message instanceof FEIBITDeviceSwitchStateRespMessage){
			FEIBITDeviceSwitchStateRespMessage msg = (FEIBITDeviceSwitchStateRespMessage)message;
			hardIdentity = (ByteUtil.shortToHexString(msg.getShortAddr()) + ByteUtil.byteToHexString(msg.getEndpoint())).toUpperCase();
			byte state = msg.getSwitchState();
			onDeviceSwitchState(connSession,hardIdentity,state);
		}
		else if(message instanceof FEIBITNodeActiveReportingMessage){//网关上报数据(即上报遥测数据)
			FEIBITNodeActiveReportingMessage msg = (FEIBITNodeActiveReportingMessage)message;
			hardIdentity = (ByteUtil.shortToHexString(msg.getShortAddr()) + ByteUtil.byteToHexString(msg.getEndpoint())).toUpperCase();
			onUploadTelemetry(connSession,hardIdentity,msg);
		}
		else if(message instanceof FEIBITInfraredDataRespMessage){//保存在网关的红外数据
			FEIBITInfraredDataRespMessage msg = (FEIBITInfraredDataRespMessage)message;
			connSession.getInfraredMap().put(Integer.toString(msg.getIrId()), msg);
		}
		else if(message instanceof FEIBITSettingDeviceRespMessage){//设置设备响应消息，比如上报时间间隔设置。
			FEIBITSettingDeviceRespMessage msg = (FEIBITSettingDeviceRespMessage)message;
			if(msg.getCommand() == Constant.SET_REPORT_INTERVAL_TIME){
				log.debug("Report interval time setting success！");
			}
			else if(msg.getCommand() == Constant.SET_DEVICE_SWITCH_STATE_REQ){
				log.debug("Switch state setting success！");
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
		log.debug("Send message to Server:{}[{}]", session.getRemoteAddress(), message.toString());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.warn("Session closed {}！",session.getRemoteAddress());
		
		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession != null && deviceApiService != null) {
			String sessionId = connSession.getSessionId();
			//设备去注册
			for(Map.Entry<String, FEIBITGetDevicesRespMessage> entry : connSession.getDevicesMap().entrySet()){
				String hardIdentity = entry.getKey();
				JSONObject jsonObj = new JSONObject(true);
	  			jsonObj.put("gatewayid", "device_id");
				deviceApiService.deregister(session,sessionId,hardIdentity,jsonObj.toString());
			}
			//释放SessionActor
			deviceApiService.closeSession(session, sessionId);
		}
		
		session.close();
		connSessionMap.remove(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		ProtocolCodecFactory codec;
		codec = new FEIBITProtocolCodecFactory();
		session.getFilterChain().addLast("protocolFilter", new ProtocolCodecFilter(codec));
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		log.info("sessionIdle!");
		
		String ip = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress(); 
		String gatewaySn = ConnectionManager.findGatewaySnByIp(ip);
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
		
		if(ConnectionManager.verifyGatewaySn(gatewaySn)){
			//获取网关当前连接设备
			ConnectionManager.getDeviceMessage(session,gatewaySn);
			//查询网关保存的红外数据
			ConnectionManager.getInfraredDataReqMessage(session,gatewaySn);
			//注册网关
			onDeviceRegister(connSession,gatewaySn);
		}
		else{
			session.close();
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		log.info("Session opened {}！",session.getRemoteAddress());
		
		String ip = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress(); 
		String gatewaySn = ConnectionManager.findGatewaySnByIp(ip);
  		ConnectionSession connSession = connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
		
		if(ConnectionManager.verifyGatewaySn(gatewaySn)){
			connSession.setGatewaySn(gatewaySn);
			connSessionMap.put(session, connSession);
			//获取网关当前连接设备
			ConnectionManager.getDeviceMessage(session,gatewaySn);
			//查询网关保存的红外数据
			ConnectionManager.getInfraredDataReqMessage(session,gatewaySn);
			//网关注册
			onDeviceRegister(connSession,gatewaySn);
		}
		else{
			session.close();
		}
	}

	
	//设备注册
	public void onDeviceRegister(ConnectionSession connSession,String hardIdentity){
		String gatewaySn = connSession.getGatewaySn();
		IoSession session = connSession.getSession();
		if(ConnectionManager.verifyGatewaySn(gatewaySn) && deviceApiService != null)
		{
			JSONObject jsonObj = new JSONObject(true);
			jsonObj.put("onlineTimeout", onlineTimeout);//设备时长大于timeout没有上报数据，则设备离线
			String sessionId = connSession.getSessionId();
 			deviceApiService.register(session,sessionId,hardIdentity, jsonObj.toString());
		}
	}
	
	//更新设备开关状态
	public static void onDeviceSwitchState(ConnectionSession connSession,String hardIdentity, byte state){
		String gatewaySn = connSession.getGatewaySn();
		String sessionId = connSession.getSessionId();
		IoSession session = connSession.getSession();
		FEIBITGetDevicesRespMessage device = connSession.getDevicesMap().get(hardIdentity);
		boolean isRegistered = connSession.getRegisteredDevices().contains(hardIdentity);
		//已注册的设备，才上报遥测
		if(ConnectionManager.verifyGatewaySn(gatewaySn) && deviceApiService != null && device != null && isRegistered){
			if(//device.getDeviceType() == Constant.CURTAIN||
				device.getDeviceType() == Constant.POWER_SWITCH){
				JSONObject jsonObj = new JSONObject(true);
				jsonObj.put("switch", state);
				deviceApiService.updateDeviceAttributes(session,sessionId,hardIdentity,jsonObj.toString());
				deviceApiService.uploadTelemetry(session,sessionId,hardIdentity,jsonObj.toString());
			}
		}
	}
	
	//上报遥测数据
	public void onUploadTelemetry(ConnectionSession connSession, String hardIdentity, FEIBITNodeActiveReportingMessage msg) {
		String gatewaySn = connSession.getGatewaySn();
		String sessionId = connSession.getSessionId();
		IoSession session = connSession.getSession();
		FEIBITGetDevicesRespMessage device = connSession.getDevicesMap().get(hardIdentity);
		boolean isRegistered = connSession.getRegisteredDevices().contains(hardIdentity);
		//设备存在，并且已注册
		if(ConnectionManager.verifyGatewaySn(gatewaySn) && deviceApiService != null && device != null && isRegistered){
			String data = msg.getTelemetryData(device);
			if(data != null || data != ""){
				deviceApiService.uploadTelemetry(session,sessionId,hardIdentity,data);
			}
		}
	}
}

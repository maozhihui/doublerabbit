package com.comba.transport.pm25.session;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.session.IoSession;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.core.BasicDeRegisterResponse;
import com.comba.server.common.msg.core.BasicRegisterResponse;
import com.comba.server.common.msg.core.BasicResponseMsg;
import com.comba.server.common.msg.core.ToDeviceRequestMsg;
import com.comba.server.common.msg.session.SessionActorToAdaptorMsg;
import com.comba.server.common.msg.session.SessionCtrlMsg;
import com.comba.server.common.msg.session.SessionType;
import com.comba.server.common.msg.session.ex.SessionException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.common.transport.session.DeviceAwareSessionContext;
import com.comba.transport.pm25.ConnectionSession;
import com.comba.transport.pm25.PM25ServerSessionHandler;
import com.comba.transport.pm25.message.PM25ReadDataRespMessage;

@Slf4j
public class TcpSessionCtx extends DeviceAwareSessionContext{
	
    private SessionId sessionId;
    private IoSession tcpConnectSession;

	public TcpSessionCtx(IoSession session,SessionMsgProcessor processor,
			DeviceAuthService authService) {
		super(processor, authService);
		tcpConnectSession = session;
	    this.sessionId = new TcpSessionId();
		// TODO Auto-generated constructor stub
	}

	@Override
	public SessionType getSessionType() {
		// TODO Auto-generated method stub
		return SessionType.ASYNC;
	}

	@Override
	public void onMsg(SessionActorToAdaptorMsg msg) throws SessionException {
		// TODO Auto-generated method stub
		
		if(msg.getMsg() instanceof BasicResponseMsg){//注册返回消息
			handleBasicResponseMsg(msg);
		}
		else if(msg.getMsg() instanceof ToDeviceRequestMsg) {//下发消息	
			log.debug("Platform send a command:[{}]！",msg.getMsg());
		}
	}

	//处理注册响应消息
	public void handleBasicResponseMsg(SessionActorToAdaptorMsg msg){
		TcpSessionCtx ctx = (TcpSessionCtx) msg.getSessionContext(); 
		String hardIdentity = ctx.device.getHardIdentity().toUpperCase();
		
		if(msg.getMsg() instanceof BasicRegisterResponse){
			ConnectionSession connSession = PM25ServerSessionHandler.connSessionMap.get(tcpConnectSession);
			if(connSession != null) {
				//缓存已注册的设备
				connSession.getRegisteredDevices().add(hardIdentity);
				PM25ServerSessionHandler.connSessionMap.put(tcpConnectSession, connSession);
				log.debug("Device register success！[{}]",hardIdentity);
			}
			else{
				log.warn("Device register message！[{}][{}]",hardIdentity,msg.getMsg());
			}
		}
		else if(msg.getMsg() instanceof BasicDeRegisterResponse){
			log.debug("Device deregister success！[{}]",hardIdentity);
		}
		else{
			log.debug("This is a response message of upload the attribute data or telemetry data！[{}]",msg.getMsg());
		}
	}
	
	@Override
	public void onMsg(SessionCtrlMsg msg) throws SessionException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SessionId getSessionId() {
		// TODO Auto-generated method stub
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = new TcpSessionId(sessionId);
	}

	@Override
	public String getRegisterToken() {
		// TODO Auto-generated method stub
		return null;
	}
}

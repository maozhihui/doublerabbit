package com.comba.server.transport.kerui.session;

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
import com.comba.server.transport.kerui.KERUIServerSessionHandler;
import com.comba.server.transport.kerui.bean.ControllerInfo;

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
			log.warn("Platform send a command:[{}]！",msg.getMsg());
		}
	}

	//处理注册响应消息
	public void handleBasicResponseMsg(SessionActorToAdaptorMsg msg){
		try {
			TcpSessionCtx ctx = (TcpSessionCtx) msg.getSessionContext(); 
			String hardIdentity = ctx.device.getHardIdentity();
			if(msg.getMsg() instanceof BasicRegisterResponse){
				//缓存已注册成功的设备硬件标识
				String regex = "-";
				String node = hardIdentity.split(regex)[0];
				ControllerInfo controllerInfo = KERUIServerSessionHandler.mastersMap.get(Integer.parseInt(node));
				controllerInfo.getDevicesMap().get(hardIdentity).setRegistered(true);
				KERUIServerSessionHandler.mastersMap.put(Integer.parseInt(node), controllerInfo);
				log.debug("Device register success！[{}]",hardIdentity);
			}
			else if(msg.getMsg() instanceof BasicDeRegisterResponse){
				log.debug("Device deregister success！[{}]",hardIdentity);
			}
			else{
				log.debug("This is a response message of upload the attribute data or telemetry data！[{}]",msg.getMsg());
			}
		}catch(Exception e){
			log.error(e.toString());
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

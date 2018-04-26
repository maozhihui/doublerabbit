package com.comba.transport.pshare.service.session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.session.IoSession;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.cmd.CommandMsgType;
import com.comba.server.common.msg.cmd.QueryCmdRequestMsg;
import com.comba.server.common.msg.cmd.SetCmdRequestMsg;
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
import com.comba.transport.pshare.ConnectionSession;
import com.comba.transport.pshare.PSHAREServerSessionHandler;
import com.comba.transport.pshare.message.PSHAREGetLockStateRespMessage;

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
			handleDeviceRequestMsg(msg);
		}
	}

	//处理注册响应消息
	public void handleBasicResponseMsg(SessionActorToAdaptorMsg msg){
		
		TcpSessionCtx ctx = (TcpSessionCtx) msg.getSessionContext(); 
		String hardIdentity = ctx.device.getHardIdentity();
		
		if(msg.getMsg() instanceof BasicRegisterResponse){
			ConnectionSession connSession = PSHAREServerSessionHandler.connSessionMap.get(tcpConnectSession);
			if(connSession != null) {
				//缓存已注册的设备
				connSession.getRegisteredDevices().add(hardIdentity.toUpperCase());
				PSHAREServerSessionHandler.connSessionMap.put(tcpConnectSession, connSession);
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
	
	//处理下发消息
	public void handleDeviceRequestMsg(SessionActorToAdaptorMsg msg){
		log.debug("Platform send a command:[{}]！",msg.getMsg());
		//下发消息请求，判断消息类型，做相应的命令操作
		ToDeviceRequestMsg reqMsg = (ToDeviceRequestMsg) msg.getMsg();
		String gatewayId = null;
		String hardIdentity = null;
		ConcurrentMap<String, String> attributeMap = null;
		List<String> attributeList = null;
		CommandMsgType cmdMsgType = reqMsg.getCmdMsg().getData().getMsgType();
		
		if(cmdMsgType == CommandMsgType.SET_REQUEST) {//设置命令
    		SetCmdRequestMsg setCmdMsg = (SetCmdRequestMsg) reqMsg.getCmdMsg().getData();
    		gatewayId = setCmdMsg.getGatewayId();
    		hardIdentity = setCmdMsg.getHardIdentity();
    		attributeMap = setCmdMsg.getAttributes();
    		
    		//设备信息，根据硬件标识找到设备，获取相关信息
    		String s[] = hardIdentity.split("-");
    		byte addr = (byte)Integer.parseInt(s[1]);
    		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
    			log.info("Setting command：[{}],[{},{}]",hardIdentity,entry.getKey(),entry.getValue());
    			PSHAREServerSessionHandler.setLockStateReqMessage(tcpConnectSession, addr, entry.getKey(),entry.getValue());
    		}
		}
		else if(cmdMsgType == CommandMsgType.QUERY_REQUEST){//查询命令
    		QueryCmdRequestMsg queryCmd = (QueryCmdRequestMsg) reqMsg.getCmdMsg().getData();
    		hardIdentity = queryCmd.getHardIdentity();
    		gatewayId = queryCmd.getGatewayId();
    		attributeList = queryCmd.getAttributes();
    		log.info("Query command：[{}],[{}]",hardIdentity,queryCmd);
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
		//this.sessionId = new TcpSessionId(UUIDUtils.toUUID(sessionId));
		this.sessionId = new TcpSessionId(sessionId);
	}

	@Override
	public String getRegisterToken() {
		// TODO Auto-generated method stub
		return null;
	}
}

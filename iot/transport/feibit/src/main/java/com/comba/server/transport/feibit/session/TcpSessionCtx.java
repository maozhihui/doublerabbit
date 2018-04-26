package com.comba.server.transport.feibit.session;

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
import com.comba.server.transport.feibit.bean.ConnectionSession;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.ConnectionManager;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesRespMessage;

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
			handleFEIBITToDeviceRequestMsg(msg);
		}
	}

	//处理注册响应消息
	public void handleBasicResponseMsg(SessionActorToAdaptorMsg msg){
		TcpSessionCtx ctx = (TcpSessionCtx) msg.getSessionContext(); 
		String hardIdentity = ctx.device.getHardIdentity().toUpperCase();
		if(msg.getMsg() instanceof BasicRegisterResponse){
			ConnectionSession connSession = FEIBITClientSessionHandler.connSessionMap.get(tcpConnectSession);
			if(connSession != null) {
				//缓存已注册的设备
				connSession.getRegisteredDevices().add(hardIdentity);
				FEIBITClientSessionHandler.connSessionMap.put(tcpConnectSession, connSession);
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
	public void handleFEIBITToDeviceRequestMsg(SessionActorToAdaptorMsg msg){
		//下发消息请求，判断消息类型，做相应的命令操作
		ToDeviceRequestMsg reqMsg = (ToDeviceRequestMsg) msg.getMsg();
		log.info("handle request message[{}]",reqMsg.getCmdMsg().getData());
		
		ConnectionSession connSession = FEIBITClientSessionHandler.connSessionMap.get(tcpConnectSession);
		if(connSession == null) {
			log.error("连接{}已断开！找不到设备所对应网关的SNID！",tcpConnectSession.getRemoteAddress());
			return;
		}
		
		String gatewayId = connSession.getGatewaySn();
		String hardIdentity = null;
		ConcurrentMap<String, String> attributeMap = null;
		List<String> attributeList = null;
		CommandMsgType cmdMsgType = reqMsg.getCmdMsg().getData().getMsgType();
		
		if(cmdMsgType == CommandMsgType.SET_REQUEST) {//设置命令
    		SetCmdRequestMsg setCmdMsg = (SetCmdRequestMsg) reqMsg.getCmdMsg().getData();
    		//gatewayId = setCmdMsg.getGatewayId();
    		hardIdentity = setCmdMsg.getHardIdentity().toUpperCase();
    		attributeMap = setCmdMsg.getAttributes();
    		
    		//设备信息，根据硬件标识找到设备，获取相关信息
    		FEIBITGetDevicesRespMessage deviceMsg = connSession.getDevicesMap().get(hardIdentity);
    		if(deviceMsg == null){
    			log.error("{}设备不在网关列表中！",hardIdentity);
    			return;
    		}
    		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
				ConnectionManager.setDeviceMessage(tcpConnectSession,gatewayId,deviceMsg,entry.getKey(),entry.getValue());
				
				//批量控制窗帘时，只有部分响应消息。为了解决窗帘状态准确实时，上报遥测数据。
				if(deviceMsg.getDeviceType() == Constant.CURTAIN){
					byte state = (byte)Integer.parseInt(entry.getValue());
					FEIBITClientSessionHandler.onDeviceSwitchState(connSession,hardIdentity,state);
				}
			}
		}
		else if(cmdMsgType == CommandMsgType.QUERY_REQUEST){//查询命令
    		QueryCmdRequestMsg queryCmd = (QueryCmdRequestMsg) reqMsg.getCmdMsg().getData();
    		hardIdentity = queryCmd.getHardIdentity().toUpperCase();
    		//gatewayId = queryCmd.getGatewayId();
    		attributeList = queryCmd.getAttributes();
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

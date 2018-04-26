package com.comba.server.common.msg.session.ctrl;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.session.SessionCtrlMsg;

public class BasicUpdateRegisterSessionMsg implements SessionCtrlMsg {

	private final SessionId sessionId;
	private final String deviceId;
	private final String registerToken;
	private final long freshTime;
	
	public BasicUpdateRegisterSessionMsg(SessionId sessionId,String deviceId,String registerToken,long freshTime) {
		this.sessionId = sessionId;
		this.deviceId = deviceId;
		this.registerToken = registerToken;
		this.freshTime = freshTime;
	}
	
	@Override
	public SessionId getSessionId() {
		return sessionId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getRegisterToken() {
		return registerToken;
	}

	public long getFreshTime() {
		return freshTime;
	}

}

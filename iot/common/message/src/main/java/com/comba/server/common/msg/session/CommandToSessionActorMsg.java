package com.comba.server.common.msg.session;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.aware.SessionAwareMsg;
import com.comba.server.common.msg.cmd.CommandMsg;

/**
 * 
 * @author maozhihui
 *
 */
public class CommandToSessionActorMsg implements SessionAwareMsg {

	private final SessionId sessionId;
	private final CommandMsg cmdMsg;
	
	public CommandToSessionActorMsg(SessionId sessionId,CommandMsg cmdMsg) {
		this.sessionId = sessionId;
		this.cmdMsg = cmdMsg;
	}
	
	@Override
	public SessionId getSessionId() {
		return sessionId;
	}

	public CommandMsg getCmdMsg() {
		return cmdMsg;
	}
	
}

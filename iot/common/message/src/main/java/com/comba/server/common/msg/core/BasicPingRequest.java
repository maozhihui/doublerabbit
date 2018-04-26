package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.MsgType;

/**
 * ping业务消息请求
 * @author maozhihui
 *
 */
public class BasicPingRequest extends BasicRequest implements PingRequest {
	
	private static final long serialVersionUID = -6877123606267026069L;

	private long freshTime = 0;
	
	public BasicPingRequest() {
		super(DEFAULT_REQUEST_ID);
		this.freshTime = System.currentTimeMillis();
	}
	
	public BasicPingRequest(long freshTime) {
		super(DEFAULT_REQUEST_ID);
		this.freshTime = freshTime;
	}
	
	@Override
	public MsgType getMsgType() {
		return MsgType.PING_REQUEST;
	}

	@Override
	public long getFreshTime() {
		return freshTime;
	}

}

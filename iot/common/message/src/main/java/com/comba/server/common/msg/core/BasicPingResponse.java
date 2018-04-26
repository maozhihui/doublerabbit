package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.MsgType;

public class BasicPingResponse extends BasicResponseMsg<PingResponseBodyMsg> {

	private static final long serialVersionUID = 1314354485649660673L;

	private BasicPingResponse(MsgType requestMsgType, Integer requestId, MsgType msgType, boolean success,
			Exception error, PingResponseBodyMsg data) {
		super(requestMsgType, requestId, msgType, success, error, data);
	}

	public static BasicPingResponse onResponse(Integer requestId,PingResponseBodyMsg bodyMsg){
		if (bodyMsg.getErrno() == ResponseBodyCode.SUCCESS.getStatusCode()) {
			return new BasicPingResponse(MsgType.PING_REQUEST,requestId,MsgType.PING_RESPONSE,true,new Exception(bodyMsg.getError()),bodyMsg);
		}else {
			return new BasicPingResponse(MsgType.PING_REQUEST,requestId,MsgType.PING_RESPONSE,false,new Exception(bodyMsg.getError()),bodyMsg);
		}
		
	}
}

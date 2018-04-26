package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.MsgType;

public class BasicTelemetryResponse extends BasicResponseMsg<TelemetryResponseBodyMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255566007061819088L;

	private BasicTelemetryResponse(MsgType requestMsgType, Integer requestId, MsgType msgType, boolean success,
			Exception error, TelemetryResponseBodyMsg data) {
		super(requestMsgType, requestId, msgType, success, error, data);
	}

	public static BasicTelemetryResponse onResponse(Integer requestId,TelemetryResponseBodyMsg bodyMsg){
		if (bodyMsg.getErrno() == ResponseBodyCode.SUCCESS.getStatusCode()) {
			return new BasicTelemetryResponse(MsgType.POST_TELEMETRY_REQUEST,requestId,MsgType.POST_TELEMETRY_REQUEST,true,new Exception(bodyMsg.getError()),bodyMsg);
		}else {
			return new BasicTelemetryResponse(MsgType.POST_TELEMETRY_REQUEST,requestId,MsgType.POST_TELEMETRY_REQUEST,false,new Exception(bodyMsg.getError()),bodyMsg);
		}
		
	}
}

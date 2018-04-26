package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.MsgType;

/**
 * 
 * @author maozhihui
 *
 */
public class BasicDeRegisterResponse extends BasicResponseMsg<DeRegisterResponseBodyMsg> {

	private static final long serialVersionUID = 99186472547679671L;

	private BasicDeRegisterResponse(MsgType requestMsgType, Integer requestId, MsgType msgType, boolean success,
			Exception error, DeRegisterResponseBodyMsg data) {
		super(requestMsgType, requestId, msgType, success, error, data);
	}
	
	public static BasicDeRegisterResponse onResponse(Integer requestId,DeRegisterResponseBodyMsg bodyMsg){
		if (bodyMsg.getErrno() == ResponseBodyCode.SUCCESS.getStatusCode()) {
			return new BasicDeRegisterResponse(MsgType.DEREGISTER,requestId,MsgType.DEREGISTER_RESPONSE,true,new Exception(bodyMsg.getError()),bodyMsg);
		}else {
			return new BasicDeRegisterResponse(MsgType.DEREGISTER,requestId,MsgType.DEREGISTER_RESPONSE,false,new Exception(bodyMsg.getError()),bodyMsg);
		}
		
	}
}

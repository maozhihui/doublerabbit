package com.comba.server.common.msg.core;

import com.comba.server.common.msg.session.MsgType;

public class BasicRegisterResponse extends BasicResponseMsg<RegisterResponseBodyMsg>{

	private static final long serialVersionUID = 1L;

    public static BasicRegisterResponse onSuccess(MsgType requestMsgType, int requestId, RegisterResponseBodyMsg body) {
        return new BasicRegisterResponse(requestMsgType, requestId, true, new Exception(body.getError()), body);
    }

    public static BasicRegisterResponse onError(MsgType requestMsgType, int requestId, RegisterResponseBodyMsg body) {
        return new BasicRegisterResponse(requestMsgType, requestId, false, new Exception(body.getError()), body);
    }

    public static BasicRegisterResponse onResponse(MsgType requestMsgType, int requestId, RegisterResponseBodyMsg body){
    	if (body.getErrno() == ResponseBodyCode.SUCCESS.getStatusCode() ) {
    		return new BasicRegisterResponse(requestMsgType, requestId, true, new Exception(body.getError()), body);
		}else {
			return new BasicRegisterResponse(requestMsgType, requestId, false, new Exception(body.getError()), body);
		}
    }
    
    private BasicRegisterResponse(MsgType requestMsgType, int requestId, boolean success, Exception error, RegisterResponseBodyMsg body) {
        super(requestMsgType, requestId, MsgType.REGISTER_RESPONSE, success, error, body);
    }
}

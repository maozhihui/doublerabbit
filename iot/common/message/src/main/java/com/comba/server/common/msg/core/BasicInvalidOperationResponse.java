package com.comba.server.common.msg.core;

import java.util.Optional;

import com.comba.server.common.msg.kv.AttributesKVMsg;
import com.comba.server.common.msg.session.MsgType;

/**
 * @author xianhongdong
 * 非法操作的响应
 */
public class BasicInvalidOperationResponse extends BasicResponseMsg<String> implements InvalidOperationResponse {

	public BasicInvalidOperationResponse(MsgType requestMsgType, int requestId, boolean success, Exception error, String info) {
        super(requestMsgType, requestId, MsgType.INVALID_OPERATION, success, error, info);
    }

}

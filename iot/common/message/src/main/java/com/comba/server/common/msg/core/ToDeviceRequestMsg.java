package com.comba.server.common.msg.core;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.comba.server.common.msg.aware.CommandAwareMsg;
import com.comba.server.common.msg.cmd.BasicCommandMsg;
import com.comba.server.common.msg.cmd.CommandMsgType;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.common.msg.session.ToDeviceMsg;

import lombok.Data;


/**
 * @author huangjinlong
 */
@Data
public class ToDeviceRequestMsg implements ToDeviceMsg {

	private BasicCommandMsg<CommandAwareMsg> cmdMsg;
	
    @Override
    public MsgType getMsgType() {
        return MsgType.TO_DEVICE_REQUEST;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}

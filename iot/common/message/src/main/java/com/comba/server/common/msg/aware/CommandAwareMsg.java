package com.comba.server.common.msg.aware;

import com.comba.server.common.msg.cmd.CommandMsgType;

/**
 * 
 * @author maozhihui
 *
 */
public interface CommandAwareMsg extends DeviceAwareMsg {
	CommandMsgType getMsgType();
}

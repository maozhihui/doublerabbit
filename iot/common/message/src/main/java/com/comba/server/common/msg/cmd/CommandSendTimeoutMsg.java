package com.comba.server.common.msg.cmd;

import java.io.Serializable;
import java.util.UUID;

/**
 * 命令发送超时
 * @author huangjinlong
 *
 */
public class CommandSendTimeoutMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID cmdMsgId;

    public CommandSendTimeoutMsg(UUID cmdMsgId) {
        super();
        this.cmdMsgId = cmdMsgId;
    }

    public UUID getMsgId() {
        return cmdMsgId;
    }

}

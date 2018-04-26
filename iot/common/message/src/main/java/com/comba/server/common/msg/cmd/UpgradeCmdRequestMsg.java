package com.comba.server.common.msg.cmd;

import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.msg.aware.CommandAwareMsg;
import lombok.Data;

import static com.comba.server.common.msg.cmd.CommandMsgType.UPGRADE_REQUEST;

@Data
public class UpgradeCmdRequestMsg implements CommandAwareMsg {

    private DeviceId deviceId;
    private String hardIdentity;


    @Override
    public CommandMsgType getMsgType() {
        return UPGRADE_REQUEST;
    }
}

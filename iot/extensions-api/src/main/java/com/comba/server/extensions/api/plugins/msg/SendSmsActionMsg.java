package com.comba.server.extensions.api.plugins.msg;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.extensions.api.rules.RuleFilter;

/**
 * @author huangjinlong
 */
@Data
@Builder
public class SendSmsActionMsg implements Serializable {

    private final String dstNum;
    private final String content;
    private final ToDeviceActorMsg toDeviceActorMsg;
    private final List<RuleFilter> filters;
}

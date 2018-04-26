package com.comba.server.extensions.core.filter;

import lombok.extern.slf4j.Slf4j;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.MsgType;

import com.comba.server.extensions.api.component.Filter;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleFilter;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andrew Shvayka
 */
@Filter(name = "Message Type Filter", descriptor = "MsgTypeFilterDescriptor.json", configuration = MsgTypeFilterConfiguration.class)
@Slf4j
public class MsgTypeFilter extends SimpleRuleLifecycleComponent implements RuleFilter<MsgTypeFilterConfiguration> {

    private List<MsgType> msgTypes;
    private boolean isMatched = false;

    @Override
    public void init(MsgTypeFilterConfiguration configuration) {
        msgTypes = Arrays.stream(configuration.getMessageTypes()).map(type -> {
            switch (type) {
                case "GET_ATTRIBUTES":
                    return MsgType.GET_ATTRIBUTES_REQUEST;
                case "POST_ATTRIBUTES":
                    return MsgType.POST_ATTRIBUTES_REQUEST;
                case "POST_TELEMETRY":
                    return MsgType.POST_TELEMETRY_REQUEST;
                case "RPC_REQUEST":
                    return MsgType.TO_SERVER_RPC_REQUEST;
                default:
                    throw new InvalidParameterException("Can't map " + type + " to " + MsgType.class.getName() + "!");
            }
        }).collect(Collectors.toList());
    }

    @Override
    public boolean filter(RuleContext ctx, ToDeviceActorMsg msg) {
        isMatched = false;
        for (MsgType msgType : msgTypes) {
            if (msgType == msg.getPayload().getMsgType()) {
                isMatched = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isMatched() {
        return isMatched;
    }
}

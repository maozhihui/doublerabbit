package com.comba.server.extensions.core.action.telemetry;

import com.comba.server.common.msg.core.GetAttributesRequest;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.common.msg.core.UpdateAttributesRequest;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.FromDeviceMsg;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.component.Action;
import com.comba.server.extensions.api.component.EmptyComponentConfiguration;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.plugins.PluginAction;
import com.comba.server.extensions.api.plugins.msg.*;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;

import java.util.Optional;

@Action(name = "Telemetry Plugin Action")
public class TelemetryPluginAction extends SimpleRuleLifecycleComponent implements PluginAction<EmptyComponentConfiguration> {

    public void init(EmptyComponentConfiguration configuration) {
    }

    @Override
    public Optional<RuleToPluginMsg<?>> convert(RuleContext ctx, ToDeviceActorMsg toDeviceActorMsg, RuleProcessingMetaData deviceMsgMd) {
    	DeviceTelemetryAttributes telemetryAttributes = ctx.getDeviceTelemetryAttributes();
        FromDeviceMsg msg = toDeviceActorMsg.getPayload();
        if (msg.getMsgType() == MsgType.POST_TELEMETRY_REQUEST) {
            TelemetryUploadRequest payload = (TelemetryUploadRequest) msg;
            return Optional.of(new TelemetryUploadRequestRuleToPluginMsg(toDeviceActorMsg.getTenantId(), toDeviceActorMsg.getCustomerId(),
                    toDeviceActorMsg.getDeviceId(), telemetryAttributes, payload));
        } else if (msg.getMsgType() == MsgType.POST_ATTRIBUTES_REQUEST) {
            UpdateAttributesRequest payload = (UpdateAttributesRequest) msg;
            return Optional.of(new UpdateAttributesRequestRuleToPluginMsg(toDeviceActorMsg.getTenantId(), toDeviceActorMsg.getCustomerId(),
                    toDeviceActorMsg.getDeviceId(), payload));
        } else if (msg.getMsgType() == MsgType.GET_ATTRIBUTES_REQUEST) {
            GetAttributesRequest payload = (GetAttributesRequest) msg;
            return Optional.of(new GetAttributesRequestRuleToPluginMsg(toDeviceActorMsg.getTenantId(), toDeviceActorMsg.getCustomerId(),
                    toDeviceActorMsg.getDeviceId(), payload));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ToDeviceMsg> convert(PluginToRuleMsg<?> response) {
        if (response instanceof ResponsePluginToRuleMsg) {
            return Optional.of(((ResponsePluginToRuleMsg) response).getPayload());
        }
        return Optional.empty();
    }

    @Override
    public boolean isOneWayAction() {
        return false;
    }
}

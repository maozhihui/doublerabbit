package com.comba.server.extensions.api.plugins.handlers;

import com.comba.server.extensions.api.plugins.msg.*;
import lombok.extern.slf4j.Slf4j;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.rules.RuleException;

/**
 * @author Andrew Shvayka
 */
@Slf4j
public class DefaultRuleMsgHandler implements RuleMsgHandler {

    @Override
    public void process(PluginContext ctx, TenantId tenantId, RuleId ruleId, RuleToPluginMsg<?> msg) throws RuleException {
        if (msg instanceof TelemetryUploadRequestRuleToPluginMsg) {
            handleTelemetryUploadRequest(ctx, tenantId, ruleId, (TelemetryUploadRequestRuleToPluginMsg) msg);
        } else if (msg instanceof UpdateAttributesRequestRuleToPluginMsg) {
            handleUpdateAttributesRequest(ctx, tenantId, ruleId, (UpdateAttributesRequestRuleToPluginMsg) msg);
        } else if (msg instanceof GetAttributesRequestRuleToPluginMsg) {
            handleGetAttributesRequest(ctx, tenantId, ruleId, (GetAttributesRequestRuleToPluginMsg) msg);
        } else if (msg instanceof SendSmsRuleToPluginActionMsg) {
            handleSendSms(ctx, tenantId, ruleId, (SendSmsRuleToPluginActionMsg) msg);
        } else if(msg instanceof BasicAlarmRuleToPluginMsg){
            handleAlarmMsg(ctx, tenantId, ruleId, (BasicAlarmRuleToPluginMsg)msg);
        } else if(msg instanceof BasicCmdRuleToPluginMsg){
            handleCmdMsg(ctx, tenantId, ruleId, (BasicCmdRuleToPluginMsg)msg);
        }
        //TODO: handle subscriptions to attribute updates.
    }

    protected void handleGetAttributesRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, GetAttributesRequestRuleToPluginMsg msg) {
        msgTypeNotSupported(msg.getPayload().getMsgType());
    }

    protected void handleUpdateAttributesRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, UpdateAttributesRequestRuleToPluginMsg msg) {
        msgTypeNotSupported(msg.getPayload().getMsgType());
    }

    protected void handleTelemetryUploadRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, TelemetryUploadRequestRuleToPluginMsg msg) {
        msgTypeNotSupported(msg.getPayload().getMsgType());
    }

    private void msgTypeNotSupported(MsgType msgType) {
        throw new RuntimeException("Not supported msg type: " + msgType + "!");
    }

	public void handleSendSms(PluginContext ctx, TenantId tenantId,
			RuleId ruleId, SendSmsRuleToPluginActionMsg msg) {
		 msgTypeNotSupported(msg.getPayload().getToDeviceActorMsg().getPayload().getMsgType());
		// TODO Auto-generated method stub
		
	}

    public void handleAlarmMsg(PluginContext ctx, TenantId tenantId,
                              RuleId ruleId, BasicAlarmRuleToPluginMsg msg) {
        //msgTypeNotSupported(msg.getPayload().getToDeviceActorMsg().getPayload().getMsgType());
    }

    public void handleCmdMsg(PluginContext ctx, TenantId tenantId,
                               RuleId ruleId, BasicCmdRuleToPluginMsg msg) {

    }
}

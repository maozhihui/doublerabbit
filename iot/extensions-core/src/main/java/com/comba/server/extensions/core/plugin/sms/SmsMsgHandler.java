package com.comba.server.extensions.core.plugin.sms;

import lombok.extern.slf4j.Slf4j;

import com.comba.server.common.data.DataConstants;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.BasicTelemetryResponse;
import com.comba.server.common.msg.core.ResponseBodyCode;
import com.comba.server.common.msg.core.TelemetryResponseBodyMsg;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.DefaultRuleMsgHandler;
import com.comba.server.extensions.api.plugins.msg.ResponsePluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.SendSmsRuleToPluginActionMsg;


@Slf4j
public class SmsMsgHandler extends DefaultRuleMsgHandler {

    @Override
    public void handleSendSms(PluginContext ctx, TenantId tenantId, RuleId ruleId, SendSmsRuleToPluginActionMsg msg) {
//    	ToDeviceActorMsg deviceMsg = msg.getPayload().getToDeviceActorMsg();	
    	TelemetryUploadRequest request = (TelemetryUploadRequest) msg.getPayload().getToDeviceActorMsg().getPayload();
    	 ctx.sendSms(msg.getTenantId(), msg.getDeviceId(), DataConstants.CLIENT_SCOPE, msg,
                 new PluginCallback<Void>() {
             @Override
             public void onSuccess(PluginContext ctx, Void data) {
            	 int errno = ResponseBodyCode.SUCCESS.getStatusCode();
             	 String error = "sms succ";
            	 TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
            	 BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
//                 ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, BasicStatusCodeResponse.onSuccess(request.getMsgType(), request.getRequestId())));
            	 ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
            	   
             }

             @Override
             public void onFailure(PluginContext ctx, Exception e) {
                 log.error("Failed to process Send sms msg", e);
            	 int errno = ResponseBodyCode.CLIENT_ERROR.getStatusCode();
             	 String error = "sms send fail";
            	 TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
            	 BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
//                 ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, BasicStatusCodeResponse.onError(request.getMsgType(), request.getRequestId(), e)));
            	 ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
             }
         });
    }
}

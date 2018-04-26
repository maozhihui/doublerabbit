package com.comba.server.extensions.core.plugin.telemetry.handlers;

import lombok.extern.slf4j.Slf4j;

import com.comba.server.common.data.DataConstants;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.kv.BasicAttributeKVMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.DefaultRuleMsgHandler;
import com.comba.server.extensions.api.plugins.msg.GetAttributesRequestRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.ResponsePluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.TelemetryUploadRequestRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.UpdateAttributesRequestRuleToPluginMsg;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TelemetryRuleMsgHandler extends DefaultRuleMsgHandler {

    @Override
    public void handleGetAttributesRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, GetAttributesRequestRuleToPluginMsg msg) {
        GetAttributesRequest request = msg.getPayload();

        List<ConfigAttributes> result = ctx.loadAttributes(msg.getDeviceId());
        BasicGetAttributesResponse response = BasicGetAttributesResponse.onSuccess(request.getMsgType(),
                request.getRequestId(), BasicAttributeKVMsg.from(result));
        ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
    }

    /*private void getAttributeKvEntries(PluginContext ctx, DeviceId deviceId, String scope, Optional<Set<String>> names, PluginCallback<List<AttributeKvEntry>> callback) {
        if (names.isPresent()) {
            if (!names.get().isEmpty()) {
                ctx.loadAttributes(deviceId, scope, new ArrayList<>(names.get()), callback);
            } else {
                ctx.loadAttributes(deviceId, scope, callback);
            }
        } else {
            callback.onSuccess(ctx, Collections.emptyList());
        }
    }*/
    
    @Override
    public void handleTelemetryUploadRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, TelemetryUploadRequestRuleToPluginMsg msg) {
        TelemetryUploadRequest request = msg.getPayload();
        // 加载数据库的遥测属性
        DeviceTelemetryAttributes originalAttributesList = msg.getTelemetryAttributes();
        // 设备上报的遥测属性
        List<KvEntry> uploadAttributesList = null;
        for (Map.Entry<Long, List<KvEntry>> entry : request.getData().entrySet()) {
        	uploadAttributesList = entry.getValue();
        }
        ctx.saveTelemetres(msg.getTenantId(), msg.getDeviceId(), DataConstants.CLIENT_SCOPE, originalAttributesList, uploadAttributesList,
                new PluginCallback<Void>() {
            @Override
            public void onSuccess(PluginContext ctx, Void data) {
                 int errno = ResponseBodyCode.SUCCESS.getStatusCode();
            	 String error = "succ";
            	 TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
            	 BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
            	 ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
            }

            @Override
            public void onFailure(PluginContext ctx, Exception e) {
                log.error("Failed to process telemetry upload request", e);
           	 	int errno = ResponseBodyCode.CLIENT_ERROR.getStatusCode();
           	 	String error = "Failed to process relemetry upload request";
           	 	TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
           	 	BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
           	 	ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
            }
        });
    }

    @Override
    public void handleUpdateAttributesRequest(PluginContext ctx, TenantId tenantId, RuleId ruleId, UpdateAttributesRequestRuleToPluginMsg msg) {
        UpdateAttributesRequest request = msg.getPayload();
        ctx.saveAttributes(msg.getTenantId(), msg.getDeviceId(), DataConstants.CLIENT_SCOPE, request.getAttributes().stream().collect(Collectors.toList()),
                new PluginCallback<Void>() {
                    @Override
                    public void onSuccess(PluginContext ctx, Void value) {
//                        ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, BasicStatusCodeResponse.onSuccess(request.getMsgType(), request.getRequestId())));
                        int errno = ResponseBodyCode.SUCCESS.getStatusCode();
                   	 	String error = "succ";
                   	 	TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
                   	 	BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
                   	 	ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
                    }

                    @Override
                    public void onFailure(PluginContext ctx, Exception e) {
                        log.error("Failed to process attributes update request", e.getMessage());
                        int errno = ResponseBodyCode.CLIENT_ERROR.getStatusCode();
                 	 	String error = "Failed to process attributes update request";
                 	 	TelemetryResponseBodyMsg bodyMsg = new TelemetryResponseBodyMsg(errno, error);
                 	 	BasicTelemetryResponse response = BasicTelemetryResponse.onResponse(request.getRequestId(), bodyMsg);
                 	 	ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
                    }
                });
    }
}
package com.comba.server.extensions.core.plugin.mq;

import com.comba.server.common.data.DataConstants;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.BasicStatusCodeResponse;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.DefaultRuleMsgHandler;
import com.comba.server.extensions.api.plugins.msg.BasicAlarmRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.ResponsePluginToRuleMsg;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 * PublisherPluginHandler
 *
 * @author maozhihui
 * @create 2017-10-12 20:22
 **/
@Slf4j
public class PublisherPluginHandler extends DefaultRuleMsgHandler {

    private static final Gson GSON = new Gson();

    @Override
    public void handleAlarmMsg(PluginContext ctx, TenantId tenantId,
                               RuleId ruleId, BasicAlarmRuleToPluginMsg msg) {

        ctx.sendMQMsg(msg.getTenantId(), msg.getDeviceId(), DataConstants.CLIENT_SCOPE, msg,
                new PluginCallback<Void>() {
                    @Override
                    public void onSuccess(PluginContext ctx, Void data) {
                        // 插件执行完成后返回给规则
                        BasicStatusCodeResponse response = BasicStatusCodeResponse.onSuccess(MsgType.POST_TELEMETRY_REQUEST,0);
                        ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));

                    }

                    @Override
                    public void onFailure(PluginContext ctx, Exception e) {
                        log.error("Failed to process Send mq msg", e);
                        BasicStatusCodeResponse response = BasicStatusCodeResponse.onError(MsgType.POST_TELEMETRY_REQUEST,0,new Exception("send mq message failed."));
                        ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
                    }
                });

    }

}

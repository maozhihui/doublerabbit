package com.comba.server.extensions.core.plugin;

import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.BasicStatusCodeResponse;
import com.comba.server.common.msg.session.MsgType;
import com.comba.server.extensions.api.plugins.PluginCallback;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.handlers.DefaultRuleMsgHandler;
import com.comba.server.extensions.api.plugins.msg.BasicCmdRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.ResponsePluginToRuleMsg;
import lombok.extern.slf4j.Slf4j;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 17:07
 **/
@Slf4j
public class CmdPluginHandler extends DefaultRuleMsgHandler {

    @Override
    public void handleCmdMsg(PluginContext ctx, TenantId tenantId,
                             RuleId ruleId, BasicCmdRuleToPluginMsg msg) {
        log.info("BasicCmdRuleToPluginMsg msg [{}]",msg);
        ctx.sendCommandMsg(tenantId, msg,
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
                        BasicStatusCodeResponse response = BasicStatusCodeResponse.onError(MsgType.POST_TELEMETRY_REQUEST,0,new Exception("send command message failed."));
                        ctx.reply(new ResponsePluginToRuleMsg(msg.getUid(), tenantId, ruleId, response));
                    }
                });
    }
}

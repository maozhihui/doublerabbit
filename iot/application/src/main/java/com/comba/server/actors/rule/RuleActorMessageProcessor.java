package com.comba.server.actors.rule;

import java.util.*;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.plugin.RuleToPluginMsgWrapper;
import com.comba.server.actors.shared.ComponentMsgProcessor;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.PluginId;
import com.comba.server.common.data.id.PluginJpaId;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleState;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.core.RuleEngineError;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.plugins.PluginAction;
import com.comba.server.extensions.api.plugins.msg.PluginToRuleMsg;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.rules.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.event.LoggingAdapter;
import com.ql.util.express.DefaultContext;

/**
 * process目前用于存储全局表达式
 */
class RuleActorMessageProcessor extends ComponentMsgProcessor<RuleId> {

    private final RuleProcessingContext ruleCtx;
    private final Map<UUID, RuleProcessingMsg> pendingMsgMap;

    private Rule ruleMd;
    private ComponentLifecycleState state;
    private List<RuleFilter> filters;
    private RuleProcessor processor;
    private PluginAction action;

    private TenantId pluginTenantId;
    private PluginId pluginId;
    // 条件表达式完成结果
    private final DefaultContext<String,Object> expressContext;

    protected RuleActorMessageProcessor(TenantId tenantId, RuleId ruleId, ActorSystemContext systemContext, LoggingAdapter logger) {
        super(systemContext, logger, tenantId, ruleId);
        this.pendingMsgMap = new HashMap<>();
        this.ruleCtx = new RuleProcessingContext(systemContext, ruleId);
        this.expressContext = new DefaultContext<>();
    }

    @Override
    public void start() throws Exception {
        logger.debug("ruleId [{}],tenantId [{}] Starting rule actor.", entityId, tenantId);
        ruleMd = systemContext.getRuleJpaService().findRuleById(entityId);
        state = ComponentLifecycleState.ACTIVE;
        if (ruleMd == null) {
            throw new RuleInitializationException("Rule not found!");
        }
       /* state = ruleMd.getState();
        if (state == ComponentLifecycleState.ACTIVE) {
            logger.info("[{}] Rule is active. Going to initialize rule components.", entityId);
            initComponent();
        } else {
            logger.info("[{}] Rule is suspended. Skipping rule components initialization.", entityId);
        }*/
        initComponent();

        logger.debug("ruleId [{}],tenantId [{}] Started rule actor.", entityId, tenantId);
    }

	@Override
    public void stop() throws Exception {
        onStop();
    }


    private void initComponent() throws RuleException {
        try {
            if (!ruleMd.getJsonFilters().isArray()) {
                throw new RuntimeException("Filters are not array!");
            }
            // 获取规则对应的插件信息
            fetchPluginInfo();
            // 初始化过滤器
            initFilters();
            initProcessor();
            initAction();
        } catch (RuntimeException e) {
            throw new RuleInitializationException("Unknown runtime exception!", e);
        } catch (InstantiationException e) {
            throw new RuleInitializationException("No default constructor for rule implementation!", e);
        } catch (IllegalAccessException e) {
            throw new RuleInitializationException("Illegal Access Exception during rule initialization!", e);
        } catch (ClassNotFoundException e) {
            throw new RuleInitializationException("Rule Class not found!", e);
        } catch (Exception e) {
            throw new RuleException(e.getMessage(), e);
        }
    }

    private void initAction() throws Exception {
    	String ruleAction = ruleMd.getAction();
        JsonNode actionMd = ruleMd.getJsonAction(ruleAction);
        action = initComponent(actionMd);
    }

    private void initProcessor() throws Exception {
        /*if (ruleMd.getProcessor() != null && !ruleMd.getProcessor().isNull()) {
            processor = initComponent(ruleMd.getProcessor());
        }*/
    }

    private void initFilters() throws Exception {
    	String ruleFilters = ruleMd.getFilters();
       filters = new ArrayList<>(ruleMd.getJsonFilters(ruleFilters).size());
        for (int i = 0; i < ruleMd.getJsonFilters(ruleFilters).size(); i++) {
            filters.add(initComponent(ruleMd.getJsonFilters(ruleFilters).get(i)));
        }
    }

    private void fetchPluginInfo() {
    	// 此处需要重构，多次ID转换多余 mzh
    	PluginJpaId pluginJpaId = new PluginJpaId(UUIDUtils.toUUID(ruleMd.getPluginId()));
        PluginJpa pluginMd = systemContext.getPluginJpaService().findPluginById(pluginJpaId);
        pluginTenantId = new TenantId(UUIDUtils.toUUID(pluginMd.getTenantId()));
        pluginId = new PluginId(pluginMd.getId().getId());
    }

    protected void onRuleProcessingMsg(ActorContext context, RuleProcessingMsg msg) throws RuleException {
        if (state != ComponentLifecycleState.ACTIVE) {
            pushToNextRule(context, msg.getCtx(), RuleEngineError.NO_ACTIVE_RULES);
            return;
        }
        // 数据转换action为null ,直接跳到下一个ruleActor, 否则在下面action.convert()会报错
        if(action == null) {
            logger.error("ruleId [{}] action configuration is empty.", entityId);
            pushToNextRule(context, msg.getCtx(), null);
            return;
        }
        ChainProcessingContext chainCtx = msg.getCtx();
        ToDeviceActorMsg inMsg = chainCtx.getInMsg();

        ruleCtx.update(inMsg,chainCtx.getTelemetryAttributes(),filters);

        for (RuleFilter filter : filters) {
            boolean filterResult = filter.filter(ruleCtx,inMsg);
            if (filter.isMatched()){
                expressContext.put(filter.getName(),filterResult);
                logger.debug("filter name [{}],result [{}]",filter.getName(),filterResult);
            }
        }
        // 判定条件链是否满足
        boolean goalResult = false;
        try {
            Object obj = systemContext.getExpressRunner().execute(ruleMd.getProcessor(),expressContext,null,true,true);
            if (obj != null){
                goalResult = (boolean)obj;
                if (goalResult){
                    // 清空规则条件判定结果
                    expressContext.clear();
                }
            }
        } catch (Exception e) {
            logger.error("ruleId [{}] filter goal express judge failed,cause [{}]",entityId,e.getMessage());
        }
        // 全局条件链执行不成功，进入下一条规则
        if (!goalResult){
            pushToNextRule(context, msg.getCtx(), RuleEngineError.NO_FILTERS_MATCHED);
            return;
        }

        RuleProcessingMetaData inMsgMd;
        if (processor != null) {
            logger.debug("ruleId [{}] going to process in msg: [{}]", entityId, inMsg);
            inMsgMd = processor.process(ruleCtx, inMsg);
        } else {
            inMsgMd = new RuleProcessingMetaData();
        }
        logger.debug("ruleId [{}] filters success,in msg [{}]",entityId,inMsg);
        Optional<RuleToPluginMsg<?>> ruleToPluginMsgOptional = action.convert(ruleCtx, inMsg, inMsgMd);
        // 是否需要经过插件处理
        if (ruleToPluginMsgOptional.isPresent()) {
            RuleToPluginMsg<?> ruleToPluginMsg = ruleToPluginMsgOptional.get();
            logger.debug("ruleId [{}] in msg is convert to [{}] by action.", entityId, ruleToPluginMsg);
            context.parent().tell(new RuleToPluginMsgWrapper(pluginTenantId, pluginId, tenantId, entityId, ruleToPluginMsg), context.self());
            // 是否需要等待响应结果
            if (action.isOneWayAction()) {
                pushToNextRule(context, msg.getCtx(), RuleEngineError.NO_TWO_WAY_ACTIONS);
            } else {
                pendingMsgMap.put(ruleToPluginMsg.getUid(), msg);
                scheduleMsgWithDelay(context, new RuleToPluginTimeoutMsg(ruleToPluginMsg.getUid()), systemContext.getPluginProcessingTimeout());
            }
        } else {
            logger.debug("ruleId [{}] nothing to send to pluginId [{}]", entityId, pluginId);
            pushToNextRule(context, msg.getCtx(), RuleEngineError.NO_REQUEST_FROM_ACTIONS);
            return;
        }
    }

    public void onPluginMsg(ActorContext context, PluginToRuleMsg<?> msg) {
        RuleProcessingMsg pendingMsg = pendingMsgMap.remove(msg.getUid());
        if (pendingMsg != null) {
            ChainProcessingContext ctx = pendingMsg.getCtx();
            Optional<ToDeviceMsg> ruleResponseOptional = action.convert(msg);
            if (ruleResponseOptional.isPresent()) {
                ctx.mergeResponse(ruleResponseOptional.get());
                pushToNextRule(context, ctx, null);
            } else {
                pushToNextRule(context, ctx, RuleEngineError.NO_RESPONSE_FROM_ACTIONS);
            }
        } else {
            logger.warning("ruleId [{}] processing timeout detected [{}]", entityId, msg.getUid());
        }
    }

    public void onTimeoutMsg(ActorContext context, RuleToPluginTimeoutMsg msg) {
        RuleProcessingMsg pendingMsg = pendingMsgMap.remove(msg.getMsgId());
        if (pendingMsg != null) {
            logger.debug("ruleId [{}] processing timeout detected [{}],msg [{}]", entityId, msg.getMsgId(), pendingMsg);
            ChainProcessingContext ctx = pendingMsg.getCtx();
            pushToNextRule(context, ctx, RuleEngineError.PLUGIN_TIMEOUT);
        }
    }

    /**
     * 执行下一条规则，如果发生失败则终止规则链
     * @param context
     * @param ctx
     * @param error
     */
    private void pushToNextRule(ActorContext context, ChainProcessingContext ctx, RuleEngineError error) {
        if (error != null) {
            ctx = ctx.withError(error);
        }
        if (ctx.isFailure()) {
            logger.debug("ruleId [{}],did [{}] forwarding processing chain to device actor due to failure.",entityId ,ctx.getInMsg().getDeviceId());
            ctx.getDeviceActor().tell(new RulesProcessedMsg(ctx), ActorRef.noSender());
        } else if (!ctx.hasNext()) {
            logger.debug("ruleId [{}],did [{}] forwarding processing chain to device actor due to end of chain.",entityId ,ctx.getInMsg().getDeviceId());
            ctx.getDeviceActor().tell(new RulesProcessedMsg(ctx), ActorRef.noSender());
        } else {
            logger.debug("ruleId [{}],did [{}] forwarding processing chain to next rule actor.", entityId ,ctx.getInMsg().getDeviceId());
            ChainProcessingContext nextTask = ctx.getNext();
            nextTask.getCurrentActor().tell(new RuleProcessingMsg(nextTask), context.self());
        }
    }

    @Override
    public void onCreated(ActorContext context) {
        logger.info("ruleId [{}] going to process onCreated rule.", entityId);
    }

    @Override
    public void onUpdate(ActorContext context) throws RuleException {
    	Rule oldRuleMd = ruleMd;
        ruleMd = systemContext.getRuleJpaService().findRuleById(entityId);
        logger.info("ruleId [{}] rule configuration was updated from [{}] to [{}].", entityId, oldRuleMd, ruleMd);
        try {
        	if (!ruleMd.getJsonFilters().isArray()) {
                throw new RuntimeException("Filters are not array!");
            }
            fetchPluginInfo();
            if (filters == null || !Objects.equals(oldRuleMd.getFilters(), ruleMd.getFilters())) {
                logger.info("ruleId [{}] rule filters require restart due to json change from [{}] to [{}].",
                        entityId, mapper.writeValueAsString(oldRuleMd.getFilters()), mapper.writeValueAsString(ruleMd.getFilters()));
                stopFilters();
                initFilters();
            }
            if (processor == null || !Objects.equals(oldRuleMd.getProcessor(), ruleMd.getProcessor())) {
                logger.info("ruleId [{}] rule processor require restart due to configuration change.", entityId);
                stopProcessor();
                initProcessor();
            }
            if (action == null || !Objects.equals(oldRuleMd.getAction(), ruleMd.getAction())) {
                logger.info("ruleId [{}] rule action require restart due to configuration change.", entityId);
                stopAction();
                initAction();
            }
        } catch (RuntimeException e) {
            throw new RuleInitializationException("Unknown runtime exception!", e);
        } catch (InstantiationException e) {
            throw new RuleInitializationException("No default constructor for rule implementation!", e);
        } catch (IllegalAccessException e) {
            throw new RuleInitializationException("Illegal Access Exception during rule initialization!", e);
        } catch (ClassNotFoundException e) {
            throw new RuleInitializationException("Rule Class not found!", e);
        } catch (JsonProcessingException e) {
            throw new RuleInitializationException("Rule configuration is invalid!", e);
        } catch (Exception e) {
            throw new RuleInitializationException(e.getMessage(), e);
        }
    }

    @Override
    public void onActivate(ActorContext context) throws Exception {
        logger.info("ruleId [{}] going to process onActivate rule.", entityId);
        this.state = ComponentLifecycleState.ACTIVE;
        if (action != null) {
            if (filters != null) {
                filters.forEach(f -> f.resume());
            } else {
                initFilters();
            }
            if (processor != null) {
                processor.resume();
            } else {
                initProcessor();
            }
            action.resume();
            logger.info("ruleId [{}] rule resumed.", entityId);
        } else {
            start();
        }
    }

    @Override
    public void onSuspend(ActorContext context) {
        logger.info("ruleId [{}] going to process onSuspend rule.", entityId);
        this.state = ComponentLifecycleState.SUSPENDED;
        if (filters != null) {
            filters.forEach(f -> f.suspend());
        }
        if (processor != null) {
            processor.suspend();
        }
        if (action != null) {
            action.suspend();
        }
    }

    @Override
    public void onStop(ActorContext context) {
        logger.info("ruleId [{}] going to process onStop rule.", entityId);
        onStop();
        scheduleMsgWithDelay(context, new RuleTerminationMsg(entityId), systemContext.getRuleActorTerminationDelay(),context.parent());
    }

    private void onStop() {
        this.state = ComponentLifecycleState.SUSPENDED;
        stopFilters();
        stopProcessor();
        stopAction();
    }

    @Override
    public void onClusterEventMsg(ClusterEventMsg msg) throws Exception {

    }

    private void stopAction() {
        if (action != null) {
            action.stop();
        }
    }

    private void stopProcessor() {
        if (processor != null) {
            processor.stop();
        }
    }

    private void stopFilters() {
        if (filters != null) {
            filters.forEach(f -> f.stop());
        }
    }
}

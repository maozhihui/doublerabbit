package com.comba.server.actors.shared.rule;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.comba.server.common.data.web.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.rule.RuleActor;
import com.comba.server.actors.rule.RuleActorChain;
import com.comba.server.actors.rule.RuleActorMetaData;
import com.comba.server.actors.rule.SimpleRuleActorChain;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.PageDataIterable.FetchFunction;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.plugin.ComponentLifecycleState;
import com.comba.server.common.data.rule.RuleMetaData;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.rule.RuleService;
/**
 * 规则管理器
 * @author maozhihui
 *
 */
@Slf4j
public abstract class RuleManager {

    protected final ActorSystemContext systemContext;
    protected final RuleJpaService ruleJpaService;
    protected final Map<RuleId, ActorRef> ruleActors;
    protected final TenantId tenantId;
    private static final int DEFAULT_WEIGHT = 3;

    Map<Rule, RuleActorMetaData> ruleMap = new HashMap<>();
    private RuleActorChain ruleChain;

    public RuleManager(ActorSystemContext systemContext, TenantId tenantId) {
        this.systemContext = systemContext;
        this.ruleJpaService = systemContext.getRuleJpaService();
        this.ruleActors = new HashMap<>();
        this.tenantId = tenantId;
    }

    public void init(ActorContext context) {
    	List<Rule> ruleIterator = getFetchRules();
        ruleMap = new HashMap<>();

        for (Rule rule : ruleIterator) {
            log.debug("[{}] Creating rule actor {}", rule.getId(), rule);
            ActorRef ref = getOrCreateRuleActor(context, rule.getId());
            RuleActorMetaData actorMd = RuleActorMetaData.systemRule(rule.getId(), DEFAULT_WEIGHT, ref);
            ruleMap.put(rule, actorMd);
            log.debug("[{}] Rule actor created.", rule.getId());
        }

        refreshRuleChain();
    }

    public Optional<ActorRef> update(ActorContext context, RuleId ruleId, ComponentLifecycleEvent event) {
        Rule rule = null;
        if (event != ComponentLifecycleEvent.DELETED) {
            rule = systemContext.getRuleJpaService().findRuleById(ruleId);
        }
        if (rule == null) {
            rule = ruleMap.keySet().stream()
                    .filter(r -> r.getId().equals(ruleId))
                    .peek(r -> r.setState(ComponentLifecycleState.SUSPENDED))
                    .findFirst()
                    .orElse(null);
        }
        if (rule != null) {
            RuleActorMetaData actorMd = ruleMap.get(rule);
            if (actorMd == null) {
                ActorRef ref = getOrCreateRuleActor(context, rule.getId());
                actorMd = RuleActorMetaData.systemRule(rule.getId(), 1, ref);
                ruleMap.put(rule, actorMd);
            }
            refreshRuleChain();
            return Optional.of(actorMd.getActorRef());
        } else {
            log.warn("[{}] Can't process unknown rule!", ruleId);
            return Optional.empty();
        }    	
    }

    abstract List<Rule> getFetchRules();
    
    abstract String getDispatcherName();

    public ActorRef getOrCreateRuleActor(ActorContext context, RuleId ruleId) {
        return ruleActors.computeIfAbsent(ruleId, rId ->
                context.actorOf(Props.create(new RuleActor.ActorCreator(systemContext, tenantId, rId))
                        .withDispatcher(getDispatcherName()), rId.toString()));
    }

    public RuleActorChain getRuleChain() {
        return ruleChain;
    }


    private void refreshRuleChain() {
        Set<RuleActorMetaData> activeRuleSet = new HashSet<>();
        for (Map.Entry<Rule, RuleActorMetaData> rule : ruleMap.entrySet()) {
            if (rule.getKey().getState() == ComponentLifecycleState.ACTIVE) {
                activeRuleSet.add(rule.getValue());
            }
        }
        ruleChain = new SimpleRuleActorChain(activeRuleSet);
    }
    public void remove(RuleId ruleId){
    	ruleActors.remove(ruleId);
    	Rule rule = ruleMap.keySet().stream().filter(r -> r.getRuleId().equals(UUIDUtils.toUUID(ruleId.getId())))
                                .findFirst().orElse(null);
    	if (rule != null)
    	    ruleMap.remove(rule);
    }
    
}

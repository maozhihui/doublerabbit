package com.comba.server.actors.shared;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;

import akka.actor.ActorContext;
import akka.event.LoggingAdapter;

public abstract class ComponentMsgProcessor<T> extends AbstractContextAwareMsgProcessor {

    protected final TenantId tenantId;
    protected final T entityId;

    protected ComponentMsgProcessor(ActorSystemContext systemContext, LoggingAdapter logger, TenantId tenantId, T id) {
        super(systemContext, logger);
        this.tenantId = tenantId;
        this.entityId = id;
    }

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;

    public abstract void onCreated(ActorContext context) throws Exception;

    public abstract void onUpdate(ActorContext context) throws Exception;

    public abstract void onActivate(ActorContext context) throws Exception;

    public abstract void onSuspend(ActorContext context) throws Exception;

    public abstract void onStop(ActorContext context) throws Exception;

    public abstract void onClusterEventMsg(ClusterEventMsg msg) throws Exception;
}

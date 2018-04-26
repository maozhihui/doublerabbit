package com.comba.server.actors.service;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.shared.ComponentMsgProcessor;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.plugin.ComponentLifecycleMsg;

import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author Andrew Shvayka
 */
public abstract class ComponentActor<T extends EntityId, P extends ComponentMsgProcessor<T>> extends ContextAwareActor {

    protected final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    private long lastPersistedErrorTs = 0L;
    protected final TenantId tenantId;
    protected final T id;
    protected P processor;
    private long messagesProcessed;
    private long errorsOccurred;

    public ComponentActor(ActorSystemContext systemContext, TenantId tenantId, T id) {
        super(systemContext);
        this.tenantId = tenantId;
        this.id = id;
    }

    protected void setProcessor(P processor) {
        this.processor = processor;
    }

    /**
     * Actor的初始化过程，在此被重写
     */
    @Override
    public void preStart() {
        try {
            processor.start();
        } catch (Exception e) {
            logger.error("[{}][{}] Failed to start {} processor: {}", tenantId, id, id.getEntityType(), e);
            e.printStackTrace();
            logAndPersist("OnStart", e, true);
            logLifecycleEvent(ComponentLifecycleEvent.STARTED, e);
        }
    }

    @Override
    public void postStop() {
        try {
            processor.stop();
            logLifecycleEvent(ComponentLifecycleEvent.STOPPED);
        } catch (Exception e) {
            logger.warning("[{}][{}] Failed to stop {} processor: {}", tenantId, id, id.getEntityType(), e.getMessage());
            logAndPersist("OnStop", e, true);
            logLifecycleEvent(ComponentLifecycleEvent.STOPPED, e);
        }
    }

    protected void onComponentLifecycleMsg(ComponentLifecycleMsg msg) {
        try {
            switch (msg.getEvent()) {
                case CREATED:
                    processor.onCreated(context());
                    break;
                case UPDATED:
                    processor.onUpdate(context());
                    break;
                case ACTIVATED:
                    processor.onActivate(context());
                    break;
                case SUSPENDED:
                    processor.onSuspend(context());
                    break;
                case DELETED:
                    processor.onStop(context());
            }
            logLifecycleEvent(msg.getEvent());
        } catch (Exception e) {
            logAndPersist("onLifecycleMsg", e, true);
            logLifecycleEvent(msg.getEvent(), e);
        }
    }

    protected void onClusterEventMsg(ClusterEventMsg msg) {
        try {
            processor.onClusterEventMsg(msg);
        } catch (Exception e) {
            logAndPersist("onClusterEventMsg", e);
        }
    }

    protected void onStatsPersistTick(EntityId entityId) {
        /*try {
            systemContext.getStatsActor().tell(new StatsPersistMsg(messagesProcessed, errorsOccurred, tenantId, entityId), ActorRef.noSender());
            resetStatsCounters();
        } catch (Exception e) {
            logAndPersist("onStatsPersistTick", e);
        }*/
    }

    private void resetStatsCounters() {
        messagesProcessed = 0;
        errorsOccurred = 0;
    }

    protected void increaseMessagesProcessedCount() {
        messagesProcessed++;
    }


    protected void logAndPersist(String method, Exception e) {
        logAndPersist(method, e, false);
    }

    private void logAndPersist(String method, Exception e, boolean critical) {
        errorsOccurred++;
        if (critical) {
            logger.warning("[{}][{}] Failed to process {} msg: {}", id, tenantId, method, e);
        } else {
            logger.debug("[{}][{}] Failed to process {} msg: {}", id, tenantId, method, e);
        }
        long ts = System.currentTimeMillis();
        if (ts - lastPersistedErrorTs > getErrorPersistFrequency()) {
            systemContext.persistError(tenantId, id, method, e);
            lastPersistedErrorTs = ts;
        }
    }

    protected void logLifecycleEvent(ComponentLifecycleEvent event) {
        logLifecycleEvent(event, null);
    }

    protected void logLifecycleEvent(ComponentLifecycleEvent event, Exception e) {
        systemContext.persistLifecycleEvent(tenantId, id, event, e);
    }

    protected abstract long getErrorPersistFrequency();
}

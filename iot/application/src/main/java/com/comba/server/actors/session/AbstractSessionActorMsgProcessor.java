package com.comba.server.actors.session;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.event.LoggingAdapter;

import java.util.Optional;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.shared.AbstractContextAwareMsgProcessor;
import com.comba.server.actors.shared.SessionTimeoutMsg;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.common.msg.device.BasicToDeviceActorMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.*;
import com.comba.server.common.msg.session.ctrl.SessionCloseMsg;

abstract class AbstractSessionActorMsgProcessor extends AbstractContextAwareMsgProcessor {

    protected final SessionId sessionId;
    protected SessionContext sessionCtx;
    protected ToDeviceActorMsg toDeviceActorMsgPrototype;

    protected AbstractSessionActorMsgProcessor(ActorSystemContext ctx, LoggingAdapter logger, SessionId sessionId) {
        super(ctx, logger);
        this.sessionId = sessionId;
    }

    protected abstract void processToDeviceActorMsg(ActorContext ctx, ToDeviceActorSessionMsg msg);

    protected abstract void processTimeoutMsg(ActorContext context, SessionTimeoutMsg msg);

    protected abstract void processToDeviceMsg(ActorContext context, ToDeviceMsg msg);

    public abstract void processClusterEvent(ActorContext context, ClusterEventMsg msg);

    protected void processSessionCtrlMsg(ActorContext ctx, SessionCtrlMsg msg) {
        if (msg instanceof SessionCloseMsg) {
            cleanupSession(ctx);
            terminateSession(ctx, sessionId);
        }
    }

    protected void cleanupSession(ActorContext ctx) {
    }

    protected void updateSessionCtx(ToDeviceActorSessionMsg msg, SessionType type) {
        sessionCtx = msg.getSessionMsg().getSessionContext();
        toDeviceActorMsgPrototype = new BasicToDeviceActorMsg(msg, type);
    }
    
    protected ToDeviceActorMsg toDeviceMsg(ToDeviceActorSessionMsg msg) {
        AdaptorToSessionActorMsg adaptorMsg = msg.getSessionMsg();
        return new BasicToDeviceActorMsg(toDeviceActorMsgPrototype, adaptorMsg.getMsg());
    }

    protected Optional<ToDeviceActorMsg> toDeviceMsg(FromDeviceMsg msg) {
        if (toDeviceActorMsgPrototype != null) {
            return Optional.of(new BasicToDeviceActorMsg(toDeviceActorMsgPrototype, msg));
        } else {
            return Optional.empty();
        }
    }

    protected Optional<ServerAddress> forwardToAppActor(ActorContext ctx, ToDeviceActorMsg toForward) {
        //Optional<ServerAddress> address = systemContext.getRoutingService().resolve(toForward.getDeviceId());
        Optional<ServerAddress> address = null;
        forwardToAppActor(ctx, toForward, address);
        return address;
    }

    protected Optional<ServerAddress> forwardToAppActorIfAdressChanged(ActorContext ctx, ToDeviceActorMsg toForward, Optional<ServerAddress> oldAddress) {
        /*Optional<ServerAddress> newAddress = systemContext.getRoutingService().resolve(toForward.getDeviceId());
        if (!newAddress.equals(oldAddress)) {
            if (newAddress.isPresent()) {
                systemContext.getRpcService().tell(newAddress.get(),
                        toForward.toOtherAddress(systemContext.getRoutingService().getCurrentServer()));
            } else {
                getAppActor().tell(toForward, ctx.self());
            }
        }
        return newAddress;*/
        
        getAppActor().tell(toForward, ctx.self());
        return null;
    }

    protected void forwardToAppActor(ActorContext ctx, ToDeviceActorMsg toForward, Optional<ServerAddress> address) {
        /*if (address.isPresent()) {
            systemContext.getRpcService().tell(address.get(),
                    toForward.toOtherAddress(systemContext.getRoutingService().getCurrentServer()));
        } else {
            getAppActor().tell(toForward, ctx.self());
        }*/
        
        getAppActor().tell(toForward, ctx.self());
    }

    public static void terminateSession(ActorContext ctx, SessionId sessionId) {
        ctx.parent().tell(new SessionTerminationMsg(sessionId), ActorRef.noSender());
        ctx.stop(ctx.self());
    }

    public DeviceId getDeviceId() {
        return toDeviceActorMsgPrototype.getDeviceId();
    }
}

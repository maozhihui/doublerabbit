package com.comba.server.actors.session;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.ContextAwareActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.actors.shared.SessionTimeoutMsg;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.core.ToDeviceSessionActorMsg;
import com.comba.server.common.msg.session.SessionCtrlMsg;
import com.comba.server.common.msg.session.SessionMsg;
import com.comba.server.common.msg.session.SessionType;
import com.comba.server.common.msg.session.ToDeviceActorSessionMsg;
import com.comba.server.common.msg.session.ctrl.SessionCloseMsg;

import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

public class SessionActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    
    private final SessionId sessionId;
    private AbstractSessionActorMsgProcessor processor;
    

    private SessionActor(ActorSystemContext systemContext, SessionId sessionId) {
        super(systemContext);
        this.sessionId = sessionId;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(-1, Duration.Inf(),
                throwable -> {
                    logger.error(throwable, "Unknown session error");
                    if (throwable instanceof Error) {
                        return OneForOneStrategy.escalate();
                    } else {
                        return OneForOneStrategy.resume();
                    }
                });
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        logger.debug("[{}] Processing: {}.", sessionId, msg);
        if (msg instanceof ToDeviceActorSessionMsg) {	// 由SessionActor到DeviceActor，相当于上行
            processDeviceMsg((ToDeviceActorSessionMsg) msg);
        }else if (msg instanceof ToDeviceSessionActorMsg) { 	// 由DeviceActor到SessionActor，相当于下行
            processToDeviceMsg((ToDeviceSessionActorMsg) msg);
        } else if (msg instanceof SessionTimeoutMsg) {
            processTimeoutMsg((SessionTimeoutMsg) msg);
        } else if (msg instanceof SessionCtrlMsg) {
            processSessionCtrlMsg((SessionCtrlMsg) msg);
        } else if (msg instanceof ClusterEventMsg) {
            processClusterEvent((ClusterEventMsg) msg);
        } else {
            logger.warning("[{}] Unknown msg: {}", sessionId, msg);
        }
    }

	private void processClusterEvent(ClusterEventMsg msg) {
        processor.processClusterEvent(context(), msg);
    }

    private void processDeviceMsg(ToDeviceActorSessionMsg msg) {
        initProcessor(msg);
        processor.processToDeviceActorMsg(context(), msg);
    }

    private void processToDeviceMsg(ToDeviceSessionActorMsg msg) {
        processor.processToDeviceMsg(context(), msg.getMsg());
    }

    private void processTimeoutMsg(SessionTimeoutMsg msg) {
        if (processor != null) {
            processor.processTimeoutMsg(context(), msg);
        } else {
            logger.warning("[{}] Can't process timeout msg: {} without processor", sessionId, msg);
        }
    }

    private void processSessionCtrlMsg(SessionCtrlMsg msg) {
        if (processor != null) {
            processor.processSessionCtrlMsg(context(), msg);
        } else if (msg instanceof SessionCloseMsg) {
            AbstractSessionActorMsgProcessor.terminateSession(context(), sessionId);
        } else {
            logger.warning("[{}] Can't process session ctrl msg: {} without processor", sessionId, msg);
        }
    }

    private void initProcessor(ToDeviceActorSessionMsg msg) {
        if (processor == null) {
            SessionMsg sessionMsg = msg.getSessionMsg();
            if (sessionMsg.getSessionContext().getSessionType() == SessionType.SYNC) {
                processor = new SyncMsgProcessor(systemContext, logger, sessionId);
            } else {
                processor = new ASyncMsgProcessor(systemContext, logger, sessionId);
            }
        }
    }
    
    public static class ActorCreator extends ContextBasedCreator<SessionActor> {
        private static final long serialVersionUID = 1L;

        private final SessionId sessionId;

        public ActorCreator(ActorSystemContext context, SessionId sessionId) {
            super(context);
            this.sessionId = sessionId;
        }

        @Override
        public SessionActor create() throws Exception {
            return new SessionActor(context, sessionId);
        }
    }
    
}

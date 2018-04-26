package com.comba.server.actors.rule;

import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.service.ContextAwareActor;
import com.comba.server.actors.service.ContextBasedCreator;
import com.comba.server.common.msg.forward.ForwardMsgRequest;
import com.comba.server.common.msg.plugin.ForwardLifecycleMsg;

/**
 * @author maozhihui
 * @Description: 数据转发器
 * @create 2018/1/2 12:50
 **/
public class ForwardActor extends ContextAwareActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    private ForwardActorMessageProcessor processor;

    private ForwardActor(ActorSystemContext systemContext) {
        super(systemContext);
        processor = new ForwardActorMessageProcessor(systemContext,logger);
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        logger.debug("ForwardActor Received message: {}", msg);
        if (msg instanceof ForwardLifecycleMsg){
            processor.onLifecycleMsg((ForwardLifecycleMsg) msg);
        } else if (msg instanceof ForwardMsgRequest){
            processor.onForwardMsg((ForwardMsgRequest)msg);
        }
        else {
            logger.info("ForwardActor [{}] Unknown msg type.", msg);
        }
    }

    public static class ActorCreator extends ContextBasedCreator<ForwardActor> {
        private static final long serialVersionUID = 1L;

        public ActorCreator(ActorSystemContext context) {
            super(context);
        }

        @Override
        public ForwardActor create() throws Exception {
            return new ForwardActor(context);
        }
    }
}

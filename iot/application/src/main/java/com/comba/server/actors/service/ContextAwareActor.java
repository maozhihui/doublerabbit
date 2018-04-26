package com.comba.server.actors.service;

import com.comba.server.actors.ActorSystemContext;

import akka.actor.UntypedActor;

public abstract class ContextAwareActor extends UntypedActor {

    public static final int ENTITY_PACK_LIMIT = 1024;

    protected final ActorSystemContext systemContext;

    public ContextAwareActor(ActorSystemContext systemContext) {
        super();
        this.systemContext = systemContext;
    }
}

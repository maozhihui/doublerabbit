package com.comba.server.actors.rule;

import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;

import akka.actor.ActorRef;

/**
 * Immutable part of chain processing data;
 *
 * @author ashvayka
 */
public final class ChainProcessingMetaData {

    final RuleActorChain chain;
    final ToDeviceActorMsg inMsg;
    final ActorRef originator;
    final DeviceTelemetryAttributes deviceTelemetryAttributes;
    

    public ChainProcessingMetaData(RuleActorChain chain, ToDeviceActorMsg inMsg, DeviceTelemetryAttributes deviceTelemetryAttributes, ActorRef originator) {
        super();
        this.chain = chain;
        this.inMsg = inMsg;
        this.originator = originator;
        this.deviceTelemetryAttributes = deviceTelemetryAttributes;
        
    }
}

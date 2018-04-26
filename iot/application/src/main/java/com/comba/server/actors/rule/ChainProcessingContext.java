package com.comba.server.actors.rule;

import com.comba.server.common.msg.core.RuleEngineError;
import com.comba.server.common.msg.core.RuleEngineErrorMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;

import akka.actor.ActorRef;

public class ChainProcessingContext {

    private final ChainProcessingMetaData md;
    private final int index;
    private final RuleEngineError error;
    private ToDeviceMsg response;


    public ChainProcessingContext(ChainProcessingMetaData md) {
        super();
        this.md = md;
        this.index = 0;
        this.error = RuleEngineError.NO_RULES;
    }

    private ChainProcessingContext(ChainProcessingContext other, int indexOffset, RuleEngineError error) {
        super();
        this.md = other.md;
        this.index = other.index + indexOffset;
        this.error = error;
        this.response = other.response;

        if (this.index < 0 || this.index >= this.md.chain.size()) {
            throw new IllegalArgumentException("Can't apply offset " + indexOffset + " to the chain!");
        }
    }

    public ActorRef getDeviceActor() {
        return md.originator;
    }

    public ActorRef getCurrentActor() {
        return md.chain.getRuleActorMd(index).getActorRef();
    }

    public boolean hasNext() {
        return (getChainLength() - 1) > index;
    }

    public boolean isFailure() {
        return (error != null && error.isCritical()) || (response != null && !response.isSuccess());
    }

    public ChainProcessingContext getNext() {
        return new ChainProcessingContext(this, 1, this.error);
    }

    public ChainProcessingContext withError(RuleEngineError error) {
        if (error != null && (this.error == null || this.error.getPriority() < error.getPriority())) {
            return new ChainProcessingContext(this, 0, error);
        } else {
            return this;
        }
    }

    public int getChainLength() {
        return md.chain.size();
    }

    public ToDeviceActorMsg getInMsg() {
        return md.inMsg;
    }
    
    public DeviceTelemetryAttributes getTelemetryAttributes() {
        return md.deviceTelemetryAttributes;
    }
    
    public ToDeviceMsg getResponse() {
        return response;
    }

    public void mergeResponse(ToDeviceMsg response) {
        // TODO add merge logic
        this.response = response;
    }

    public RuleEngineErrorMsg getError() {
        return new RuleEngineErrorMsg(md.inMsg.getPayload().getMsgType(), error);
    }
}

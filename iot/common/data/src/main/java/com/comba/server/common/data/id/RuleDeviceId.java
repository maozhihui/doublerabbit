package com.comba.server.common.data.id;

import com.comba.server.common.data.EntityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Created by sujinxian on 2017/7/13.
 */
public class RuleDeviceId extends UUIDBased implements EntityId{

    @JsonCreator
    public RuleDeviceId(@JsonProperty("id") UUID id) {
        super(id);
    }

    @JsonIgnore
    @Override
    public EntityType getEntityType() {
        return EntityType.RULE_DEVICE;
    }
}

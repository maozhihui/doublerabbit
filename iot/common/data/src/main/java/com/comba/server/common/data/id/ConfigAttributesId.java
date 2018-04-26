package com.comba.server.common.data.id;

import java.util.UUID;

import com.comba.server.common.data.EntityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigAttributesId extends UUIDBased implements EntityId {

	private static final long serialVersionUID = 1L;

	@JsonCreator
    public ConfigAttributesId(@JsonProperty("id") UUID id) {
        super(id);
    }
	
	@JsonIgnore
	@Override
	public EntityType getEntityType() {
		// TODO Auto-generated method stub
		return EntityType.TENANT;
	}

}

package com.comba.server.common.data.id;

import java.util.UUID;

import com.comba.server.common.data.EntityType;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceId extends UUIDBased implements EntityId {

	private static final long serialVersionUID = 1L;

	@JsonCreator
    public DeviceId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static DeviceId fromString(String deviceId) {
        return new DeviceId(UUID.fromString(deviceId));
    }
    //不包含-的UUID字串
    public static DeviceId fromString2(String deviceId) {
    	String deviceIdStr = UUIDUtils.toUUID(deviceId).toString();
        return new DeviceId(UUID.fromString(deviceIdStr));
    }

    @JsonIgnore
	@Override
	public EntityType getEntityType() {
		// TODO Auto-generated method stub
		return EntityType.TENANT;
	}

}

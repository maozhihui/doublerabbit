/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.comba.server.common.data.id;

import java.util.UUID;

import com.comba.server.common.data.EntityType;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TenantId extends UUIDBased implements EntityId {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public TenantId(@JsonProperty("id") UUID id) {
        super(id);
    }
    // tenantId 包含-字串
    public static TenantId fromString(String tenantId) {
        return new TenantId(UUID.fromString(tenantId));
    }
 // tenantId 不包含-字串
    public static TenantId fromString2(String tenantId) {
    	String tenantIdStr = UUIDUtils.toUUID(tenantId).toString();
        return new TenantId(UUID.fromString(tenantIdStr));
    }
    
    @JsonIgnore
    @Override
    public EntityType getEntityType() {
        return EntityType.TENANT;
    }
}

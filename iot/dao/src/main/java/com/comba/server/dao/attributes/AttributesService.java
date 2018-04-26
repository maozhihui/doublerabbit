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
package com.comba.server.dao.attributes;

import com.google.common.util.concurrent.ListenableFuture;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.id.UUIDBased;
import com.comba.server.common.data.kv.AttributeKvEntry;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
public interface AttributesService {

    //ListenableFuture<Optional<AttributeKvEntry>> find(EntityId entityId, String scope, String attributeKey);

    //ListenableFuture<List<AttributeKvEntry>> find(EntityId entityId, String scope, Collection<String> attributeKeys);

    //ListenableFuture<List<AttributeKvEntry>> findAll(EntityId entityId, String scope);

    //ListenableFuture<List<ResultSet>> save(EntityId entityId, String scope, List<AttributeKvEntry> attributes);

    //ListenableFuture<List<ResultSet>> removeAll(EntityId entityId, String scope, List<String> attributeKeys);
    
	List<AttributeKvEntry> save(EntityId entityId, String scope, List<AttributeKvEntry> attributes);
	List<AttributeKvEntry> find(EntityId entityId, String scope, Collection<String> attributeKeys);
	List<AttributeKvEntry> findAll(EntityId entityId, String scope);
}

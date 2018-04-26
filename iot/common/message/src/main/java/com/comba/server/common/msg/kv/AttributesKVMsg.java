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
package com.comba.server.common.msg.kv;

import java.io.Serializable;
import java.util.List;

import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.kv.AttributeKey;
import com.comba.server.common.data.kv.AttributeKvEntry;

public interface AttributesKVMsg extends Serializable {
	List<ConfigAttributes> getAttributes();
    /*List<AttributeKvEntry> getClientAttributes();
    List<AttributeKvEntry> getSharedAttributes();
    List<AttributeKey> getDeletedAttributes();*/
}

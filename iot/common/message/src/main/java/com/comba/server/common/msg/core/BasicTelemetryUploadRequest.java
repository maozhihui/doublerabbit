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
package com.comba.server.common.msg.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.session.MsgType;

public class BasicTelemetryUploadRequest extends BasicRequest implements TelemetryUploadRequest {

    private static final long serialVersionUID = 1L;
    private String hardIdentity;
    private final Map<Long, List<KvEntry>> data;

    public BasicTelemetryUploadRequest(String hardIdentity) {
        this(hardIdentity,DEFAULT_REQUEST_ID);
    }

    public BasicTelemetryUploadRequest(String hardIdentity, Integer requestId) {
        super(requestId);
        this.hardIdentity = hardIdentity;
        this.data = new HashMap<>();
    }

    public void add(long ts, KvEntry entry) {
        List<KvEntry> tsEntries = data.get(ts);
        if (tsEntries == null) {
            tsEntries = new ArrayList<>();
            data.put(ts, tsEntries);
        }
        tsEntries.add(entry);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.POST_TELEMETRY_REQUEST;
    }

    @Override
    public Map<Long, List<KvEntry>> getData() {
        return data;
    }
    
    @Override
    public String getHardIdentity() {
        return hardIdentity;
    }

    @Override
    public String toString() {
        return "BasicTelemetryUploadRequest [hardIdentity=" + hardIdentity + ",data=" + data + "]";
    }

}
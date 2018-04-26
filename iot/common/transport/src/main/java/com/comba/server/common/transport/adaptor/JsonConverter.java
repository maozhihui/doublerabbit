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
package com.comba.server.common.transport.adaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.kv.*;
import com.comba.server.common.msg.kv.AttributesKVMsg;

public class JsonConverter {

    private static final Gson GSON = new Gson();

    public static TelemetryUploadRequest convertToTelemetry(String deviceId, JsonElement jsonObject) throws JsonSyntaxException {
        return convertToTelemetry(deviceId, jsonObject, BasicRequest.DEFAULT_REQUEST_ID);
    }

    public static TelemetryUploadRequest convertToTelemetry(String deviceId, JsonElement jsonObject, int requestId) throws JsonSyntaxException {
        BasicTelemetryUploadRequest request = new BasicTelemetryUploadRequest(deviceId,requestId);
        long systemTs = System.currentTimeMillis();
        if (jsonObject.isJsonObject()) {
            parseObject(request, systemTs, jsonObject);
        } else if (jsonObject.isJsonArray()) {
            jsonObject.getAsJsonArray().forEach(je -> {
                if (je.isJsonObject()) {
                    parseObject(request, systemTs, je.getAsJsonObject());
                } else {
                    throw new JsonSyntaxException("Can't parse value: " + je);
                }
            });
        } else {
            throw new JsonSyntaxException("Can't parse value: " + jsonObject);
        }
        return request;
    }

    public static ToServerRpcRequestMsg convertToServerRpcRequest(JsonElement json, int requestId) throws JsonSyntaxException {
        JsonObject object = json.getAsJsonObject();
        return new ToServerRpcRequestMsg(requestId, object.get("method").getAsString(), GSON.toJson(object.get("params")));
    }

    private static void parseObject(BasicTelemetryUploadRequest request, long systemTs, JsonElement jsonObject) {
        JsonObject jo = jsonObject.getAsJsonObject();
        if (jo.has("ts") && jo.has("values")) {
            parseWithTs(request, jo);
        } else {
            parseWithoutTs(request, systemTs, jo);
        }
    }

    private static void parseWithoutTs(BasicTelemetryUploadRequest request, long systemTs, JsonObject jo) {
        for (KvEntry entry : parseValues(jo)) {
            request.add(systemTs, entry);
        }
    }

    public static void parseWithTs(BasicTelemetryUploadRequest request, JsonObject jo) {
        long ts = jo.get("ts").getAsLong();
        JsonObject valuesObject = jo.get("values").getAsJsonObject();
        for (KvEntry entry : parseValues(valuesObject)) {
            request.add(ts, entry);
        }
    }

    public static List<KvEntry> parseValues(JsonObject valuesObject) {
        List<KvEntry> result = new ArrayList<>();
        for (Entry<String, JsonElement> valueEntry : valuesObject.entrySet()) {
            JsonElement element = valueEntry.getValue();
            if (element.isJsonPrimitive()) {
                JsonPrimitive value = element.getAsJsonPrimitive();
                if (value.isString()) {
                    result.add(new StringDataEntry(valueEntry.getKey(), value.getAsString()));
                } else if (value.isBoolean()) {
                    result.add(new BooleanDataEntry(valueEntry.getKey(), value.getAsBoolean()));
                } else if (value.isNumber()) {
                    if (value.getAsString().contains(".")) {
                        result.add(new DoubleDataEntry(valueEntry.getKey(), value.getAsDouble()));
                    } else {
                        result.add(new LongDataEntry(valueEntry.getKey(), value.getAsLong()));
                    }
                } else {
                    throw new JsonSyntaxException("Can't parse value: " + value);
                }
            } else {
                throw new JsonSyntaxException("Can't parse value: " + element);
            }
        }
        return result;
    }

    public static UpdateAttributesRequest convertToAttributes(JsonElement element) {
        return convertToAttributes(element, BasicRequest.DEFAULT_REQUEST_ID);
    }

    public static UpdateAttributesRequest convertToAttributes(JsonElement element, int requestId) {
        if (element.isJsonObject()) {
            BasicUpdateAttributesRequest request = new BasicUpdateAttributesRequest(requestId);
            long ts = System.currentTimeMillis();
            request.add(parseValues(element.getAsJsonObject()).stream().map(kv -> new BaseAttributeKvEntry(kv, ts)).collect(Collectors.toList()));
            return request;
        } else {
            throw new JsonSyntaxException("Can't parse value: " + element);
        }
    }

    public static JsonObject toJson(AttributesKVMsg payload, boolean asMap) {
        JsonObject result = new JsonObject();
        payload.getAttributes().forEach(addToObject(result));
        		
        /*if (asMap) {
            if (!payload.getClientAttributes().isEmpty()) {
                JsonObject attrObject = new JsonObject();
                payload.getClientAttributes().forEach(addToObject(attrObject));
                result.add("client", attrObject);
            }
            if (!payload.getSharedAttributes().isEmpty()) {
                JsonObject attrObject = new JsonObject();
                payload.getSharedAttributes().forEach(addToObject(attrObject));
                result.add("shared", attrObject);
            }
        } else {
            payload.getClientAttributes().forEach(addToObject(result));
            payload.getSharedAttributes().forEach(addToObject(result));
        }
        if (!payload.getDeletedAttributes().isEmpty()) {
            JsonArray attrObject = new JsonArray();
            payload.getDeletedAttributes().forEach(addToObject(attrObject));
            result.add("deleted", attrObject);
        }*/
        return result;
    }
    private static Consumer<ConfigAttributes> addToObject(JsonObject result){
    	return de->{
            JsonPrimitive value = null;
    	    if(de.getValue() != null){
                value = new JsonPrimitive(de.getValue());
            }
    		result.add(de.getAttributeName(), value);
    	};
    }

    private static Consumer<AttributeKey> addToObject(JsonArray result) {
        return key -> {
            result.add(key.getAttributeKey());
        };
    }

    /*private static Consumer<AttributeKvEntry> addToObject(JsonObject result) {
        return de -> {
            JsonPrimitive value;
            switch (de.getDataType()) {
                case BOOLEAN:
                    value = new JsonPrimitive(de.getBooleanValue().get());
                    break;
                case DOUBLE:
                    value = new JsonPrimitive(de.getDoubleValue().get());
                    break;
                case LONG:
                    value = new JsonPrimitive(de.getLongValue().get());
                    break;
                case STRING:
                    value = new JsonPrimitive(de.getStrValue().get());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported data type: " + de.getDataType());
            }
            result.add(de.getKey(), value);
        };
    }*/

    public static JsonObject toJson(ToDeviceRpcRequestMsg msg, boolean includeRequestId) {
        JsonObject result = new JsonObject();
        if (includeRequestId) {
            result.addProperty("id", msg.getRequestId());
        }
        result.addProperty("method", msg.getMethod());
        result.add("params", new JsonParser().parse(msg.getParams()));
        return result;
    }

    public static JsonElement toJson(ToServerRpcResponseMsg msg) {
        return new JsonParser().parse(msg.getData());
    }

    public static JsonElement toErrorJson(String errorMsg) {
        JsonObject error = new JsonObject();
        error.addProperty("error", errorMsg);
        return error;
    }

    public static JsonElement toJson(RegisterResponseBodyMsg msg){
    	JsonObject result = new JsonObject();
    	result.addProperty("errno", msg.getErrno());
    	result.addProperty("error", msg.getError());
    	if (!msg.getToken().isEmpty()) {
			JsonObject data = new JsonObject();
			data.addProperty("token", msg.getToken());
			result.add("data", data);
		}
    	return result;
    }
    
    public static JsonElement toJson(DeRegisterResponseBodyMsg msg){
    	JsonObject result = new JsonObject();
    	result.addProperty("errno", msg.getErrno());
    	result.addProperty("error", msg.getError());
    	return result;
    }
    
    public static JsonElement toJson(PingResponseBodyMsg msg){
    	JsonObject result = new JsonObject();
    	result.addProperty("errno", msg.getErrno());
    	result.addProperty("error", msg.getError());
    	if (!msg.getUpdateAttributeData().isEmpty()) {
    		JsonArray jsonArray = new JsonArray();
    		for (String devId : msg.getUpdateAttributeData()) {
				jsonArray.add(devId);
			}
			result.add("updateAttributeData", jsonArray);
		}
    	if (!msg.getReportAtrributeData().isEmpty()) {
    		JsonArray jsonArray = new JsonArray();
    		for (String devId : msg.getReportAtrributeData()) {
				jsonArray.add(devId);
			}
			result.add("reportAtrributeData", jsonArray);
		}
    	if (!msg.getReportTelemetryData().isEmpty()) {
    		JsonArray jsonArray = new JsonArray();
    		for (String devId : msg.getReportTelemetryData()) {
				jsonArray.add(devId);
			}
			result.add("reportTelemetryData", jsonArray);
		}
        if (!msg.getUpgradeGWData().isEmpty()) {
            JsonArray jsonArray = new JsonArray();
            for (String devId : msg.getUpgradeGWData()) {
                jsonArray.add(devId);
            }
            result.add("upgradeGWData", jsonArray);
        }
    	return result;
    }
    
    public static JsonElement toJson(TelemetryResponseBodyMsg msg){
    	JsonObject result = new JsonObject();
    	result.addProperty("errno", msg.getErrno());
    	result.addProperty("error", msg.getError());
    	
    	return result;
    }
    
	public static BasicRegisterRequest convertToRegisterRequest(String deviceId, JsonElement jsonObject) {
		BasicRegisterRequest request = new BasicRegisterRequest(deviceId);
		JsonObject jo = jsonObject.getAsJsonObject();
		for (KvEntry entry : parseValues(jo)) {
            request.addEntry(entry);
        }
		return request;
	}
	public static BasicDeregisterRequest convertToDeRegisterRequest(String token,String deviceId, JsonElement jsonObject) {
		BasicDeregisterRequest request = new BasicDeregisterRequest(token,deviceId);
		JsonElement gwElement = ((JsonObject)jsonObject).get("gatewayid");
		if (gwElement != null) {
			request.setGateWayId(gwElement.getAsString());
		}
		JsonArray jo = ((JsonObject)jsonObject).getAsJsonArray("devices");
		if (jo != null) {
			for (JsonElement element : jo.getAsJsonArray()) {
				request.addDeviceId(element.getAsString());
			}
		}
		/*for (KvEntry entry : parseValues(jo)) {
            request.addEntry(entry);
        }*/
		return request;
	}
	
	public static BasicPingRequest convertToPingRequest(JsonElement jsonObject){
		long freshTime = System.currentTimeMillis();
		if (jsonObject.isJsonObject()) {
			freshTime = ((JsonObject)jsonObject).get("time").getAsLong();
		}
		return new BasicPingRequest(freshTime);
		
	}


	public static String convertToString(Object o){
	    return GSON.toJson(o);
    }
}

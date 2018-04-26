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
package com.comba.server.transport.http.session;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.comba.server.common.data.id.DeviceSessionId;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.session.*;
import com.comba.server.common.msg.session.ex.SessionException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.adaptor.JsonConverter;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.common.transport.session.DeviceAwareSessionContext;

import java.util.function.Consumer;

/**
 * @author Andrew Shvayka
 */
@Slf4j
public class HttpSessionCtx extends DeviceAwareSessionContext {

    private SessionId sessionId;
    private final long timeout;
    private final DeferredResult<ResponseEntity> responseWriter;

    protected String registerSessionToken;
    public HttpSessionCtx(SessionMsgProcessor processor, DeviceAuthService authService, DeferredResult<ResponseEntity> responseWriter, long timeout) {
        super(processor, authService);
        this.sessionId = new HttpSessionId();
        this.responseWriter = responseWriter;
        this.timeout = timeout;
    }
    public HttpSessionCtx(SessionMsgProcessor processor, DeviceAuthService authService, DeferredResult<ResponseEntity> responseWriter, long timeout,String token) {
        super(processor, authService);
        this.sessionId = new HttpSessionId();
        this.responseWriter = responseWriter;
        this.timeout = timeout;
        this.registerSessionToken = token;
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.SYNC;
    }

    @Override
    public void onMsg(SessionActorToAdaptorMsg source) throws SessionException {
        ToDeviceMsg msg = source.getMsg();
        switch (msg.getMsgType()) {
            case GET_ATTRIBUTES_RESPONSE:
                reply((GetAttributesResponse) msg);
                return;
            case STATUS_CODE_RESPONSE:
                reply((StatusCodeResponse) msg);
                return;
            case ATTRIBUTES_UPDATE_NOTIFICATION:
                reply((AttributesUpdateNotification) msg);
                return;
            case TO_DEVICE_RPC_REQUEST:
                reply((ToDeviceRpcRequestMsg) msg);
                return;
            case TO_SERVER_RPC_RESPONSE:
                reply((ToServerRpcResponseMsg) msg);
                return;
            case RULE_ENGINE_ERROR:
                reply((RuleEngineErrorMsg) msg);
                return;
            case REGISTER_RESPONSE:
            	reply((BasicRegisterResponse)msg);
            	return;
            case DEREGISTER_RESPONSE:
            	reply((BasicDeRegisterResponse)msg);
            	return;
            case PING_RESPONSE:
            	reply((BasicPingResponse)msg);
            	return;
            case POST_TELEMETRY_REQUEST:
            	reply((BasicTelemetryResponse)msg);
            	return;
            case INVALID_OPERATION:
            	reply((InvalidOperationResponse)msg);
            	return;
        }
    }

    private void reply(BasicRegisterResponse msg) {
    	HttpStatus status = HttpStatus.OK;
    	/*if (!msg.isSuccess()) {
			status = HttpStatus.BAD_REQUEST;
		}*/
    	
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg.getData().get()).toString(), status));
    }
    
    private void reply(BasicDeRegisterResponse msg) {
    	HttpStatus status = HttpStatus.OK;
    	/*if (!msg.isSuccess()) {
			status = HttpStatus.BAD_REQUEST;
		}*/
    	
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg.getData().get()).toString(), status));
    }
    
    private void reply(BasicPingResponse msg) {
    	HttpStatus status = HttpStatus.OK;
    	/*if (!msg.isSuccess()) {
			status = HttpStatus.BAD_REQUEST;
		}*/
    	
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg.getData().get()).toString(), status));
    }
    private void reply(BasicTelemetryResponse msg) {
    	HttpStatus status = HttpStatus.OK;
    	/*if (!msg.isSuccess()) {
			status = HttpStatus.BAD_REQUEST;
		}*/
    	
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg.getData().get()).toString(), status));
    }
    private void reply(RuleEngineErrorMsg msg) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (msg.getError()) {
            case PLUGIN_TIMEOUT:
                status = HttpStatus.REQUEST_TIMEOUT;
                break;
            default:
                if (msg.getInMsgType() == MsgType.TO_SERVER_RPC_REQUEST) {
                    status = HttpStatus.BAD_REQUEST;
                }
                break;
        }
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toErrorJson(msg.getErrorMsg()).toString(), status));
    }

    private <T> void reply(ResponseMsg<? extends T> msg, Consumer<T> f) {
        if (!msg.getError().isPresent()) {
            f.accept(msg.getData().get());
        } else {
            Exception e = msg.getError().get();
            responseWriter.setResult(new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private void reply(ToDeviceRpcRequestMsg msg) {
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg, true).toString(), HttpStatus.OK));
    }

    private void reply(ToServerRpcResponseMsg msg) {
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg).toString(), HttpStatus.OK));
    }

    private void reply(AttributesUpdateNotification msg) {
        responseWriter.setResult(new ResponseEntity<>(JsonConverter.toJson(msg.getData(), false).toString(), HttpStatus.OK));
    }

    private void reply(GetAttributesResponse msg) {
        reply(msg, payload -> {
        	
            if (payload.getAttributes().isEmpty()) {
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else {
                JsonObject result = JsonConverter.toJson(payload, false);
                responseWriter.setResult(new ResponseEntity<>(result.toString(), HttpStatus.OK));
            }
        });
    }
    
    /**
     * 返回错误信息给设备
     * @param msg
     */
    private void reply(InvalidOperationResponse msg) {
    	
    	JsonObject retObj = new JsonObject();
    	retObj.addProperty("errno", "403");
    	retObj.addProperty("error", msg.getError().isPresent() ? msg.getError().get().getMessage() : msg.getData().get());
    	
    	responseWriter.setResult(new ResponseEntity<>(retObj.toString(), HttpStatus.OK));
    }

    private void reply(StatusCodeResponse msg) {
        reply(msg, payload -> {
            if (payload == 0) {
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.OK));
            } else {
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.valueOf(payload)));
            }
        });
    }

    @Override
    public void onMsg(SessionCtrlMsg msg) throws SessionException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public SessionId getSessionId() {
    	/*if (sessionId == null) {
			if (getDevice() != null) {
				sessionId = new DeviceSessionId(getDevice().getId().getId());
			}else {
				sessionId = new HttpSessionId();
			}
		}*/
        return sessionId;
    }
    @Override
    public String getRegisterToken(){
    	return registerSessionToken;
    }
    
    public void setRegisterToken(String token){
    	this.registerSessionToken = token;
    }
}

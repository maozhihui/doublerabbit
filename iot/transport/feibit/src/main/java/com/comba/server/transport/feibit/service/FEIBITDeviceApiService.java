package com.comba.server.transport.feibit.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.data.security.DeviceCredentialsFilter;
import com.comba.server.common.data.security.DeviceIdCredentials;
import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.core.BasicDeregisterRequest;
import com.comba.server.common.msg.core.BasicRegisterRequest;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.common.msg.core.UpdateAttributesRequest;
import com.comba.server.common.msg.session.AdaptorToSessionActorMsg;
import com.comba.server.common.msg.session.BasicAdaptorToSessionActorMsg;
import com.comba.server.common.msg.session.BasicToDeviceActorSessionMsg;
import com.comba.server.common.msg.session.FromDeviceMsg;
import com.comba.server.common.msg.session.ctrl.SessionCloseMsg;
import com.comba.server.common.msg.session.ex.DeviceInvalidPasswordException;
import com.comba.server.common.msg.session.ex.DeviceInvalidTokenException;
import com.comba.server.common.msg.session.ex.DeviceNotExistException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.adaptor.JsonConverter;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.transport.feibit.session.TcpSessionCtx;
import com.comba.server.transport.feibit.session.TcpSessionId;

@Slf4j
@Service
public class FEIBITDeviceApiService {
    @Autowired(required = false)
    private SessionMsgProcessor processor;
    @Autowired(required = false)
    private DeviceAuthService authService;
    //private static final String sessionId = UUID.randomUUID().toString();
    
    //设备注册
    public void register(IoSession session,String sessionId,String deviceId,String json){
    	TcpSessionCtx ctx = new TcpSessionCtx(session,processor,authService);
    	BasicRegisterRequest request = JsonConverter.convertToRegisterRequest(deviceId,new JsonParser().parse(json));
    	DeviceCredentialsFilter filter = new DeviceIdCredentials(deviceId);
    	
    	try {
			if(ctx.login(deviceId,filter))
			{
				ctx.setSessionId(sessionId);
				process(ctx, request);
			}
			else
			{
				log.error("username or pwd is invalid");
				return;	
			}
    	}catch (DeviceInvalidTokenException e1) {
				// TODO Auto-generated catch block
			//responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
    		log.error("[{}]invalid token!",deviceId);
		}catch(DeviceInvalidPasswordException e2){
			log.error("[{}]password error!",deviceId);
		}catch(DeviceNotExistException e3){
			log.error("[{}]deviceId is not exists!",deviceId);
		}catch(JsonSyntaxException ex){
			log.error("request json syntax invalid:[{}]",json);
		}
    	
    }
    
    public void deregister(IoSession session,String sessionId,String deviceId,String json){
    	  	
    	TcpSessionCtx ctx = new TcpSessionCtx(session,processor,authService);
    	DeviceCredentialsFilter filter = new DeviceIdCredentials(deviceId);
    	try {
    		if(ctx.login(deviceId,filter))
			{
    			RegisterSession registerSession = authService.getDeviceRegisterSession(UUIDUtils.toUUID(ctx.getDevice().getDevId()).toString());
    			if(registerSession == null || registerSession.getToken() == "")
    				return;
				BasicDeregisterRequest request = JsonConverter.convertToDeRegisterRequest(registerSession.getToken(),deviceId,new JsonParser().parse(json));
				process(ctx, request);
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			log.error("request json syntax invalid:[{}]",json);
		} catch (DeviceInvalidTokenException e1) {
			// TODO Auto-generated catch block
			log.error("[{}]invalid token!",deviceId);
		}catch(DeviceInvalidPasswordException e2){
			log.error("[{}]password error!",deviceId);
		}catch(DeviceNotExistException e3){
			log.error("[{}]deviceId is not exists!",deviceId);
		}
    }
    
    //遥测属性上报
    public void uploadTelemetry(IoSession session,String sessionId,String deviceId,String json){

    	try {
	       	TcpSessionCtx ctx = new TcpSessionCtx(session,processor,authService);
	    	TelemetryUploadRequest request = JsonConverter.convertToTelemetry(deviceId, new JsonParser().parse(json));
	    	DeviceCredentialsFilter filter = new DeviceIdCredentials(deviceId);

			if(ctx.login(deviceId,filter))
			{
				ctx.setSessionId(sessionId);
				process(ctx, request);
			}
			else
			{
				log.error("username or pwd is invalid");
				return;	
			}
    	}catch (DeviceInvalidTokenException e1) {
				// TODO Auto-generated catch block
			//responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
    		log.error("[{}]invalid token!",deviceId);
		}catch(DeviceInvalidPasswordException e2){
			log.error("[{}]password error!",deviceId);
		}catch(DeviceNotExistException e3){
			log.error("[{}]deviceId is not exists!",deviceId);
		}catch(JsonSyntaxException ex){
			log.error("request json syntax invalid:[{}]",json);
		}
   }

	//网关连接的设备 参数上报
	public void updateDeviceAttributes(IoSession session,String sessionId,String deviceId,String json){
		
    	try {
	       	TcpSessionCtx ctx = new TcpSessionCtx(session,processor,authService);
	 	    UpdateAttributesRequest request = JsonConverter.convertToAttributes(new JsonParser().parse(json));
	    	DeviceCredentialsFilter filter = new DeviceIdCredentials(deviceId);

			if(ctx.login(deviceId,filter))
			{
				ctx.setSessionId(sessionId);
				process(ctx, request);
			}
			else
			{
				log.error("username or pwd is invalid");
				return;	
			}
    	}catch (DeviceInvalidTokenException e1) {
				// TODO Auto-generated catch block
			//responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
    		log.error("[{}]invalid token!",deviceId);
		}catch(DeviceInvalidPasswordException e2){
			log.error("[{}]password error!",deviceId);
		}catch(DeviceNotExistException e3){
			log.error("[{}]deviceId is not exists!",deviceId);
		}catch(JsonSyntaxException ex){
			log.error("request json syntax invalid:[{}]",json);
		}
    }
    
	//断开session,释放资源
	public void closeSession(IoSession session,String sessionId){
		TcpSessionCtx ctx = new TcpSessionCtx(session,processor,authService);
		SessionId id = new TcpSessionId(sessionId);
		SessionCloseMsg request = SessionCloseMsg.onDisconnect(id); 
		ctx.setSessionId(sessionId);
		processor.process(request);
	}
	
	 private void process(TcpSessionCtx ctx, FromDeviceMsg request) {
	        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, request);
	        processor.process(new BasicToDeviceActorSessionMsg(ctx.getDevice(), msg));
	    }

}

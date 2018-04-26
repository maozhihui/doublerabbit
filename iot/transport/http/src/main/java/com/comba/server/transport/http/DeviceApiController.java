package com.comba.server.transport.http;

import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.security.DeviceCredentials;
import com.comba.server.common.data.security.DeviceCredentialsFilter;
import com.comba.server.common.data.security.DeviceIdCredentials;
import com.comba.server.common.data.security.DeviceTokenCredentials;
import com.comba.server.common.data.web.utils.StringHelper;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.session.AdaptorToSessionActorMsg;
import com.comba.server.common.msg.session.BasicAdaptorToSessionActorMsg;
import com.comba.server.common.msg.session.BasicToDeviceActorSessionMsg;
import com.comba.server.common.msg.session.FromDeviceMsg;
import com.comba.server.common.msg.session.ex.DeviceInvalidPasswordException;
import com.comba.server.common.msg.session.ex.DeviceInvalidTokenException;
import com.comba.server.common.msg.session.ex.DeviceNotExistException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.adaptor.JsonConverter;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.dao.upgrade.record.UpgradeRecordService;
import com.comba.server.dao.upgrade.task.UpgradeTaskService;
import com.comba.server.dao.util.DateConvertUtil;
import com.comba.server.transport.http.session.HttpSessionCtx;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 描述：设备与平台的通信协议接口
 * 
 * @author Andrew Shvayka
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeviceApiController {

    @Value("${http.request_timeout}")
    private long defaultTimeout;
    @Autowired(required = false)
    private SessionMsgProcessor processor;

    @Autowired(required = false)
    private DeviceAuthService authService;

    private static final String GATEWAY_KEY = "gatewayid";
    private static final String USERNAME_KEY = "username";
    private static final String PWD_KEY = "pwd";
    @Autowired
    private UpgradeRecordService upgradeRecordService;

    @Autowired
    private UpgradeTaskService taskService;

    @Value("${upgrade.user}")
    private String user;

    @Value("${upgrade.password}")
    private String password;

    @Value("${upgrade.upgrade}")
    private String upgrade;

    @Value("${upgrade.host}")
    private String host;

    @Value("${upgrade.protocol}")
    private String ftp;

    @Value("${upgrade.port}")
    private String port;

    @Value("${upgrade.filePath}")
    private String filePath;

    @Value("${gate-way.logFilePath}")
    private String logFilePath;
    
    // 鉴权开关量
    @Value("${device.setting.AuthEnable}")
    private boolean authEnable;
    
    /**
     * 设备注册接口
     * 
     * 描述：用于向平台请求接入。根据设备唯一标识，检查设备是否存在；若存在，则进行鉴权操作；
     * 鉴权通过后，返回令牌token给设备。后面的心跳请求，设备属性上报，设备属性获取，
     * 设备遥测数据上报流程都需要通过token与平台交互。
     * @param deviceId 设备唯一标识
     * @return
     */
    @RequestMapping(value = "/{deviceId}/register", method = RequestMethod.POST, produces = "application/json")
    public DeferredResult<ResponseEntity> register(@PathVariable("deviceId")String deviceId,@RequestBody String json)
    {
        log.debug("deviceId  {} is registering now  message {}, time {}",deviceId,json,new Date());
    	DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
    	HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
    	
    	BasicRegisterRequest request = JsonConverter.convertToRegisterRequest(deviceId,new JsonParser().parse(json));
    	Map<String, String> paramMap = new HashMap<String, String>();
    	for (KvEntry entry : request.getData()) {
			paramMap.put(entry.getKey(), entry.getStrValue().get());
		}
    	//检查是否存在网关，网关是否合法 
    	String gatewayHardIdentity = paramMap.get(GATEWAY_KEY);
    	String gatewayId = "";
    	if (StringHelper.isNotEmpty(gatewayHardIdentity)) {
    		if (ctx.checkDeviceExist(new DeviceIdCredentials(gatewayHardIdentity))) {
				gatewayId = ctx.getDevice().getDevId();
				log.debug("gateway hardware identity [{}],device id [{}]",gatewayHardIdentity,gatewayId);
			}else {
				log.error("gateway hardware identity [{}] is invalid",gatewayHardIdentity);
				
				responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,"device not exist");	
				return responseWriter;
			}
		}
    	
    	// 判断鉴权类型
    	DeviceCredentialsFilter filter = new DeviceIdCredentials(deviceId);
    	if (authEnable) {
    		if (StringHelper.isNotEmpty(paramMap.get(USERNAME_KEY)) && StringHelper.isNotEmpty(paramMap.get(PWD_KEY))) {
				filter = new DeviceCredentials(paramMap.get(USERNAME_KEY), paramMap.get(PWD_KEY));
			}else {
				// 用户名与密码两者之一为空串或未携带
				log.error("username or pwd is invalid");
				responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,"username or pwd is invalid");
				return responseWriter;
			}
		}
    	
    	try {
			if(ctx.login(deviceId,filter))
			{
				if (StringHelper.isNotEmpty(gatewayId)) {
					ctx.getDevice().setGatewayId(gatewayId);
				}
				process(ctx, request);
			}
			else
			{
				responseError(responseWriter,ResponseBodyCode.SERVER_ERROR,"server unknow error");	
			}
    	}catch (DeviceInvalidTokenException e1) {
				// TODO Auto-generated catch block
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}
    	
    	return responseWriter;
    }
    void responseError(DeferredResult<ResponseEntity> responseWriter,ResponseBodyCode code,String error){
    	JsonObject result = new JsonObject();
    	result.addProperty("errno", code.getStatusCode());
    	result.addProperty("error", error);
    	
    	responseWriter.setResult(new ResponseEntity<>(result.toString(), HttpStatus.OK));
    }
    
    /**
     * 设备去注册接口
     * 
     * 描述：设备请求去注册，通知平台令牌token失效，设备为离线状态。
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/{deviceId}/deregister", method = RequestMethod.POST, produces = "application/json")
    public DeferredResult<ResponseEntity> deRegister(@PathVariable("deviceId")String deviceId,
    												@RequestHeader("X-Authorization")String registerToken,
    												@RequestBody String json)
    {
    	DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();    	
    	HttpSessionCtx ctx = getHttpSessionCtx(responseWriter,registerToken);
    	try {
			if (ctx.login(deviceId,new DeviceTokenCredentials(registerToken))){
				BasicDeregisterRequest request = JsonConverter.convertToDeRegisterRequest(registerToken,deviceId,new JsonParser().parse(json));
				process(ctx, request);
			}else{
				responseError(responseWriter,ResponseBodyCode.SERVER_ERROR,"server unknow error");	
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request json format invalid");
		} catch (DeviceInvalidTokenException e1) {
			// TODO Auto-generated catch block
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}
    	return responseWriter;
    }
    /**
     * 设备ping心跳接口
     * 
     * 描述：用于设备与平台之间链接，当平台下发命令时，通过ping包携带返回给设备。
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/{deviceId}/ping", method = RequestMethod.PUT, produces = "application/json")
    public DeferredResult<ResponseEntity> onPing(@PathVariable("deviceId")String deviceId,
    											@RequestHeader("X-Authorization")String registerToken,
    											@RequestBody String json)
    {
    	DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
    	HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
    	try {
			if (ctx.login(deviceId, new DeviceTokenCredentials(registerToken))){
				BasicPingRequest request = JsonConverter.convertToPingRequest(new JsonParser().parse(json));
				process(ctx, request);
			}else{			
				responseError(responseWriter,ResponseBodyCode.SERVER_ERROR,"server unknow error");
			}
		} catch (JsonSyntaxException e) {
			responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request json format invalid");
		} catch (DeviceInvalidTokenException e1) {
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}
    	return responseWriter;
    }
    
    /**
     * 设备属性获取接口
     * 
     * 描述：从平台获取到设备的所有配置属性
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/{deviceId}/attributes", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<ResponseEntity> getDeviceAttributes(@PathVariable("deviceId") String deviceId,
                                                              @RequestParam(value = "needTotal", required = true, defaultValue = "true") boolean needTotal,
                                                              @RequestHeader("X-Authorization")String registerToken
                                                              ) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        try {
			if (ctx.login(deviceId, new DeviceTokenCredentials(registerToken))){
			    GetAttributesRequest request;
			    request = new BasicGetAttributesRequest(0,null,null,needTotal);
			    process(ctx, request);
			} else {
				responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
			}
		} catch (DeviceInvalidTokenException e1) {
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}

        return responseWriter;
    }
    /**
     * 设备属性上报接口
     * 
     * 描述：将设备的配置属性上报给平台，若上报的配置属性存在，则更新属性值，否则返回上报失败。
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/{deviceId}/attributes", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postDeviceAttributes(@PathVariable("deviceId") String deviceId,
    														   @RequestHeader("X-Authorization")String registerToken,
                                                               @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        try {
			if (ctx.login(deviceId, new DeviceTokenCredentials(registerToken))){
				try {
			        process(ctx, JsonConverter.convertToAttributes(new JsonParser().parse(json)));
			    } catch (IllegalStateException | JsonSyntaxException ex) {
			        responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request syntax invalid");
			    }
			}else{
				responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
			}
		} catch (DeviceInvalidTokenException e1) {
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}
        return responseWriter;
    }
    
    /**
     * 设备遥测数据上报接口
     * 
     * 描述：设备将遥测数据上报给平台。
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/{deviceId}/telemetry", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postTelemetry(@PathVariable("deviceId") String deviceId,
    													@RequestHeader("X-Authorization")String registerToken,
                                                        @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        try {
			if (ctx.login(deviceId, new DeviceTokenCredentials(registerToken))){
			    try {
			        process(ctx, JsonConverter.convertToTelemetry(deviceId, new JsonParser().parse(json)));
			    } catch (IllegalStateException | JsonSyntaxException ex) {
			    	responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request json syntax invalid");
			    }
			} else {
				responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
			}
		} catch (DeviceInvalidTokenException e1) {
			responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
		}catch(DeviceInvalidPasswordException e2){
			responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
		}catch(DeviceNotExistException e3){
			responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
		}
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/rpc", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<ResponseEntity> subscribeToCommands(@PathVariable("deviceToken") String deviceToken,
                                                              @RequestParam(value = "timeout", required = false, defaultValue = "0") long timeout) {
        return subscribe(deviceToken, timeout, new RpcSubscribeMsg());
    }

    @RequestMapping(value = "/{deviceToken}/rpc/{requestId}", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> replyToCommand(@PathVariable("deviceToken") String deviceToken,
                                                         @PathVariable("requestId") Integer requestId,
                                                         @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        if (ctx.login(new DeviceTokenCredentials(deviceToken))) {
            try {
                JsonObject response = new JsonParser().parse(json).getAsJsonObject();
                process(ctx, new ToDeviceRpcResponseMsg(requestId, response.toString()));
            } catch (IllegalStateException | JsonSyntaxException ex) {
                //responseWriter.setResult(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            	responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request json syntax invalid");
            }
        } else {
            //responseWriter.setResult(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
        	responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
        }
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/rpc", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postRpcRequest(@PathVariable("deviceToken") String deviceToken,
                                                         @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        if (ctx.login(new DeviceTokenCredentials(deviceToken))) {
            try {
                JsonObject request = new JsonParser().parse(json).getAsJsonObject();
                process(ctx, new ToServerRpcRequestMsg(0,
                        request.get("method").getAsString(),
                        request.get("params").toString()));
            } catch (IllegalStateException | JsonSyntaxException ex) {
                //responseWriter.setResult(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            	responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request json syntax invalid");
            }
        } else {
            //responseWriter.setResult(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
        	responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
        }
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/attributes/updates", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<ResponseEntity> subscribeToAttributes(@PathVariable("deviceToken") String deviceToken,
                                                                @RequestParam(value = "timeout", required = false, defaultValue = "0") long timeout) {
        return subscribe(deviceToken, timeout, new AttributesSubscribeMsg());
    }

    private DeferredResult<ResponseEntity> subscribe(String deviceToken, long timeout, FromDeviceMsg msg) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter, timeout,deviceToken);
        if (ctx.login(new DeviceTokenCredentials(deviceToken))) {
            try {
                process(ctx, msg);
            } catch (IllegalStateException | JsonSyntaxException ex) {
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
            }
        } else {
            responseWriter.setResult(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
        }
        return responseWriter;
    }

    private HttpSessionCtx getHttpSessionCtx(DeferredResult<ResponseEntity> responseWriter) {
        return getHttpSessionCtx(responseWriter, defaultTimeout,"");
    }
    private HttpSessionCtx getHttpSessionCtx(DeferredResult<ResponseEntity> responseWriter,String registerToken) {
        return getHttpSessionCtx(responseWriter, defaultTimeout,registerToken);
    }

    private HttpSessionCtx getHttpSessionCtx(DeferredResult<ResponseEntity> responseWriter, long timeout,String registerToken) {
        return new HttpSessionCtx(processor, authService, responseWriter, timeout != 0 ? timeout : defaultTimeout,registerToken);
    }

    private void process(HttpSessionCtx ctx, FromDeviceMsg request) {
        AdaptorToSessionActorMsg msg = new BasicAdaptorToSessionActorMsg(ctx, request);
        processor.process(new BasicToDeviceActorSessionMsg(ctx.getDevice(), msg));
    }


    /**
     * 设备请求获取升级文件路径
     * 返回：ftp账号密码，服务器端口地址和路径
     *
     * @param devId 设备id
     * @param registerToken
     * @return
     */
    @PostMapping(value = "/{devId}/softwareUpgrade",produces = "application/json")
    public DeferredResult<ResponseEntity>  getDeviceSoftwareUpgrade(@PathVariable String devId,
                                                                    @RequestHeader(value = "X-Authorization") String registerToken){
        log.info("设备请求获取升级文件路径,{}",devId);
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        try {
            if (ctx.login(devId, new DeviceTokenCredentials(registerToken))){
                try {
                    List<String> path = upgradeRecordService.getUpdatePath(devId, UpgradeRecord.INIT);
                    if (path == null || path.isEmpty()){
                        responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"update file path not found");
                        return responseWriter;
                    }

                    Map<String,Object> params = new HashMap<>();
                    params.put("host",host);
                    params.put("protocol",ftp);
                    params.put("port",port);
                    params.put("user",user);
                    params.put("passwd",password);
                    params.put("path",filePath+path.get(0));
                    params.put("upgrade",upgrade);

                    //获取该设备最新那条记录id
                    UpgradeRecord record = upgradeRecordService.queryLatestRecordByDevId(devId);
                    if (record == null){
                        responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"record not found");
                        return responseWriter;
                    }

                    //判断记录状态是否正确
                    if (!UpgradeRecord.INIT.equals(record.getStatus())){
                        responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"record not init");
                        return responseWriter;
                    }

                    //更新升级记录为已通知设备成功
                    upgradeRecordService.updateRecordStatus(record.getId(), UpgradeRecord.SEND);

                    log.info(" 返回设备升级信息 ，{}",JsonConverter.convertToString(new ResponseEntity<>(JsonConverter.convertToString(params), HttpStatus.OK)));
                    responseWriter.setResult(new ResponseEntity<>(JsonConverter.convertToString(params), HttpStatus.OK));
                } catch (IllegalStateException | JsonSyntaxException ex) {
                    responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request syntax invalid");
                }
            }else{
                responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
            }
        } catch (DeviceInvalidTokenException e1) {
            responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
        }catch(DeviceInvalidPasswordException e2){
            responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
        }catch(DeviceNotExistException e3){
            responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
        }

        return responseWriter;
    }

    /**
     * 设备升级成功后，通知平台
     * 设备返回码 0-OK ，601-软件包Md5值检验错误 ，升级失败，602-校验正确但升级失败
     *
     * @param devId 设备id
     * @param registerToken
     * @return
     */
    @PutMapping(value = "/{devId}/softwareUpgrade",produces = "application/json")
    public DeferredResult<ResponseEntity> softwareUpgradeResp(@PathVariable String devId,
                                                                               @RequestParam String errno,
                                                                               @RequestParam String error,
                                                                               @RequestHeader(value = "X-Authorization") String registerToken){
        log.info("设备升级成功后，通知平台,{}",devId);
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);

        try {
            if (ctx.login(devId, new DeviceTokenCredentials(registerToken))){
                try {
                    //获取该设备最新那条记录id
                    UpgradeRecord record = upgradeRecordService.queryLatestRecordByDevId(devId);
                    if (record == null){
                        responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"record not found ");
                        return responseWriter;
                    }

                    if (errno.equals("0")){
                        //判断记录状态是否正确
                        if (!UpgradeRecord.SEND.equals(record.getStatus())){
                            responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"record not send  ");
                            return responseWriter;
                        }
                        //更新设备升级记录为升级成功状态。
                        upgradeRecordService.updateRecordStatus(record.getId(), UpgradeRecord.NOTIFY_SUCCESS);
                        //更新任务成功个数
                        taskService.updateTaskSuccessNum(devId);
                    }else {
                        //更新设备升级记录为升级失败状态。
                        log.error("设备升级失败，devId{},message{}",devId,error);
                        upgradeRecordService.updateRecordStatus(record.getId(), UpgradeRecord.NOTIFY_FAIL);
                    }

                    responseWriter.setResult(new ResponseEntity<>(HttpStatus.OK));

                } catch (IllegalStateException | JsonSyntaxException ex) {
                    responseError(responseWriter,ResponseBodyCode.CLIENT_ERROR,"request syntax invalid");
                }
            }else{
                responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,"invalid token");
            }
        } catch (DeviceInvalidTokenException e1) {
            responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
        }catch(DeviceInvalidPasswordException e2){
            responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
        }catch(DeviceNotExistException e3){
            responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
        }
        return responseWriter;
    }

    /**
     * 网关日志上传，分为即时上传和持续上传
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/{devId}/uploadLogFile",produces = "application/json")
    public DeferredResult<ResponseEntity>  uploadLogFile( @PathVariable String devId,
                                                          @RequestHeader(value = "X-Authorization") String registerToken,
                                                          HttpServletRequest request) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        log.info("接收网关日志上传请求，devId {}",devId);
        try {
            if (ctx.login(devId, new DeviceTokenCredentials(registerToken))) {
                CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
                try {
                    if (multipartResolver.isMultipart(request)) {

                        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                        Iterator<String> iterator = multipartRequest.getFileNames();
                        while (iterator.hasNext()) {
                            MultipartFile mf = multipartRequest.getFile(iterator.next());
                            if (mf != null) {
                                log.info("接收网关日志上传，devId{}，{}",devId, mf.getOriginalFilename());
                                File tempFile = new File(logFilePath + mf.getOriginalFilename());
                                mf.transferTo(tempFile);
                            }
                        }
                        responseWriter.setResult(new ResponseEntity<>(HttpStatus.OK));
                    }
                } catch (Exception e) {
                    log.error("网关上传日志文件失败，devId{}，{}",devId, e.getMessage());
                    responseError(responseWriter,ResponseBodyCode.SERVER_ERROR,e.getMessage());
                }
            }
        } catch (DeviceInvalidTokenException e1) {
            responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
        }catch(DeviceInvalidPasswordException e2){
            responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
        }catch(DeviceNotExistException e3){
            responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
        }

        return responseWriter;
    }


    /**
     * 设备告警接口
     *
     * @param devId         设备id
     * @param notifyType    告警类型 1-告警通知 2-告警恢复
     * @param alarmID       告警id
     * @param content       告警内容
     * @param alarmLevel    告警等级 1-紧急，2-重要，3一般
     * @param time          告警时间
     * @param registerToken
     * @return
     */
    @PostMapping(value = "/{devId}/alarm",produces = "application/json")
    public DeferredResult<ResponseEntity>  alarm( @PathVariable String devId,
                                                          @RequestParam Integer notifyType,
                                                          @RequestParam String alarmID,
                                                          @RequestParam String content,
                                                          @RequestParam Integer alarmLevel,
                                                          @RequestParam String time,
                                                          @RequestHeader(value = "X-Authorization") String registerToken
                                                  ) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<>();
        HttpSessionCtx ctx = getHttpSessionCtx(responseWriter);
        Date date = DateConvertUtil.strToDate(time);
        if (date == null){
            responseError(responseWriter,ResponseBodyCode.INVALID_PARAM,"日期格式不对");
        }

        try {
            if (ctx.login(devId, new DeviceTokenCredentials(registerToken))) {
                BasicAlarmUploadRequest request = new BasicAlarmUploadRequest(devId,notifyType,alarmLevel,alarmID,content, date);
                process(ctx,request);
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.OK));
            }
        } catch (DeviceInvalidTokenException e1) {
            responseError(responseWriter,ResponseBodyCode.INVALID_TOKEN,e1.getMessage());
        }catch(DeviceInvalidPasswordException e2){
            responseError(responseWriter,ResponseBodyCode.PASSWORD_ERROR,e2.getMessage());
        }catch(DeviceNotExistException e3){
            responseError(responseWriter,ResponseBodyCode.DEVICE_ID_NOT_EXISTS,e3.getMessage());
        }

        return responseWriter;
    }

}

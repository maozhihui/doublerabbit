package com.comba.server.actors.device;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.event.LoggingAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.comba.server.common.msg.cmd.*;
import com.comba.server.common.msg.forward.ForwardMsgRequest;

import com.comba.server.actors.ActorSystemContext;
import com.comba.server.actors.rule.ChainProcessingContext;
import com.comba.server.actors.rule.ChainProcessingMetaData;
import com.comba.server.actors.rule.RuleProcessingMsg;
import com.comba.server.actors.rule.RulesProcessedMsg;
import com.comba.server.actors.session.AbstractCmdMsg;
import com.comba.server.actors.shared.AbstractContextAwareMsgProcessor;
import com.comba.server.actors.shared.RegisterSessionTimeoutMsg;
import com.comba.server.actors.tenant.RuleChainDeviceMsg;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceStatus;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.DeviceSessionId;
import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.aware.CommandAwareMsg;
import com.comba.server.common.msg.cluster.ClusterEventMsg;
import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.FromDeviceMsg;
import com.comba.server.common.msg.session.SessionType;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import com.comba.server.dao.util.DaoUtil;
import com.comba.server.extensions.api.device.DeviceAttributesEventNotificationMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributesUpdateNotificationMsg;
import com.comba.server.extensions.api.plugins.msg.FromDeviceRpcResponse;
import com.comba.server.extensions.api.plugins.msg.RpcError;
import com.comba.server.extensions.api.plugins.msg.TimeoutMsg;
import com.comba.server.extensions.api.plugins.msg.ToDeviceRpcRequestPluginMsg;
import com.comba.server.extensions.api.plugins.msg.ToPluginRpcResponseDeviceMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Andrew Shvayka
 */
public class DeviceActorMessageProcessor extends AbstractContextAwareMsgProcessor {

    private final DeviceId deviceId;
    private final Map<SessionId, SessionInfo> sessions;
	private final Map<Integer, ToDeviceRpcRequestMetadata> rpcPendingMap;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DeviceTelemetryAttributes deviceTelemetryAttributes;
    
    private final static int ON_LINE = 1;
    private final static int OFF_LINE = 0;
    private final static String GATEWAY_KEY = "gatewayid";
    
    // 注册会话
    private RegisterSession session;
    // WEB端通知的查询与设备命令缓存
    private ConcurrentMap<UUID, CommandMsg> cmdMsgMap = new ConcurrentHashMap<>();
    // 命令下发，超时时间
    private long timeout = 0L;
	private SessionId sessionId;
	private SessionType sessionType;
	// 保存设备最新数据上报时间
	private long lastDataTime;
	//在线状态超时时间
	private long onlineStateTimeout;
    public DeviceActorMessageProcessor(ActorSystemContext systemContext, LoggingAdapter logger, DeviceId deviceId) {
        super(systemContext, logger);
        this.deviceId = deviceId;
        this.sessions = new HashMap<>();
        this.rpcPendingMap = new HashMap<>();
        initAttributes();
        
        this.session = systemContext.getDeviceAuthService().getDeviceRegisterSession(deviceId.getId().toString());
        if(this.session == null){
        	this.session = new RegisterSession("",deviceId.getId().toString(), RegisterSession.Status.OFFLINE);
        }
        this.lastDataTime = systemContext.getLatestDataService().getLatestTime(UUIDUtils.toUUID(deviceId.getId()));
        this.cmdMsgMap = new ConcurrentHashMap<>();
        this.timeout = systemContext.getDeviceSettingTimeout();
        this.onlineStateTimeout = systemContext.getGlobalOnlineStateTimeout();
    }

    private void initAttributes() {
    	this.deviceTelemetryAttributes = new DeviceTelemetryAttributes(fetchTelemetryAttributes(UUIDUtils.toUUID(deviceId.getId())));
    }

    public void processTimeout(ActorContext context, TimeoutMsg msg) {
        ToDeviceRpcRequestMetadata requestMd = rpcPendingMap.remove(msg.getId());
        if (requestMd != null) {
            logger.debug("[{}] RPC request [{}] timeout detected!", deviceId, msg.getId());
            ToPluginRpcResponseDeviceMsg responsePluginMsg = toPluginRpcResponseMsg(requestMd.getMsg(), requestMd.isSent() ? RpcError.TIMEOUT : RpcError.NO_ACTIVE_CONNECTION);
            context.parent().tell(responsePluginMsg, ActorRef.noSender());
        }
    }

    /**
     * 当前为注册、PING、去注册业务及参数查设命令
     * @param context
     * @param msg
     */
    void process(ActorContext context, ToDeviceActorMsg msg) {
    	FromDeviceMsg request = msg.getPayload();
    	ToDeviceSessionActorMsg responseMsg = null;
    	if(msg.getSessionType() == SessionType.ASYNC){
    		sessionId = msg.getSessionId();
    		sessionType = SessionType.ASYNC;
    	}
    	String statusMsg = "";
    	if(request instanceof RegisterRequest){
    		if (session.getStatus() == RegisterSession.Status.OFFLINE){
				statusMsg = systemContext.getGson().toJson(new DeviceStatus(UUIDUtils.toUUID(deviceId.getId()),ON_LINE));
			}
    		responseMsg = new BasicToDeviceSessionActorMsg(processRegisterRequest((BasicRegisterRequest)request), msg.getSessionId());
    	}else if(request instanceof DeregisterRequest){
    		responseMsg = new BasicToDeviceSessionActorMsg(processDeregisterRequest((BasicDeregisterRequest)request),msg.getSessionId());
			statusMsg = systemContext.getGson().toJson(new DeviceStatus(UUIDUtils.toUUID(deviceId.getId()),OFF_LINE));
    	}else if (request instanceof PingRequest) {
    		responseMsg = new BasicToDeviceSessionActorMsg(processPingRequest((BasicPingRequest)request),msg.getSessionId());
		}else{
			logger.debug("Not need to process response message[{}]",request);
			return;
		}

    	// 将响应消息发往SessionManagerActor
    	sendMsgToSessionActor(responseMsg, null);
    	// 当有在线
    	if (StringUtils.isNotBlank(statusMsg)){
			context.parent().tell(new ForwardMsgRequest("","status",statusMsg),ActorRef.noSender());
		}
    }

    void processAttributesUpdate(ActorContext context, DeviceAttributesEventNotificationMsg msg) {
    }

    void process(ActorContext context, RuleChainDeviceMsg srcMsg) {
    	// 如果是订阅模式，此处进行设备在线状态更新 2017-10-11 add by mzh
    	sessionType = srcMsg.getToDeviceActorMsg().getSessionType();
		if (session.getStatus() == RegisterSession.Status.OFFLINE){
			Device device = new Device(deviceId);
			device.setStatus(ON_LINE);
			if (systemContext.getDeviceService().updateStatus(device)){
				logger.debug("status changed online deviceId [{}]",deviceId.getId());
				session.setStatus(RegisterSession.Status.ONLINE);
				// TODO 监控状态通知
				String forwardMsg = systemContext.getGson().toJson(new DeviceStatus(UUIDUtils.toUUID(deviceId.getId()),1));
				context.parent().tell(new ForwardMsgRequest("","status",forwardMsg),ActorRef.noSender());
			}
		}
		
        ChainProcessingMetaData md = new ChainProcessingMetaData(srcMsg.getRuleChain(),
                srcMsg.getToDeviceActorMsg(),deviceTelemetryAttributes, context.self());
        ChainProcessingContext ctx = new ChainProcessingContext(md);
        if (ctx.getChainLength() > 0) {
        	logger.debug("rule size [{}]",ctx.getChainLength());
            RuleProcessingMsg msg = new RuleProcessingMsg(ctx);
            ActorRef ruleActorRef = ctx.getCurrentActor();
            ruleActorRef.tell(msg, ActorRef.noSender());
            this.lastDataTime = System.currentTimeMillis();
        } else {
            context.self().tell(new RulesProcessedMsg(ctx), context.self());
        }
    }

    void processRpcResponses(ActorContext context, ToDeviceActorMsg msg) {
    }

    public void processClusterEventMsg(ClusterEventMsg msg) {
    }

    private ToPluginRpcResponseDeviceMsg toPluginRpcResponseMsg(ToDeviceRpcRequestPluginMsg requestMsg, String data) {
        return toPluginRpcResponseMsg(requestMsg, data, null);
    }

    private ToPluginRpcResponseDeviceMsg toPluginRpcResponseMsg(ToDeviceRpcRequestPluginMsg requestMsg, RpcError error) {
        return toPluginRpcResponseMsg(requestMsg, null, error);
    }

    private ToPluginRpcResponseDeviceMsg toPluginRpcResponseMsg(ToDeviceRpcRequestPluginMsg requestMsg, String data, RpcError error) {
        return new ToPluginRpcResponseDeviceMsg(
                requestMsg.getPluginId(),
                requestMsg.getPluginTenantId(),
                new FromDeviceRpcResponse(requestMsg.getMsg().getId(),
                        data,
                        error
                )
        );
    }

    void onRulesProcessedMsg(ActorContext context, RulesProcessedMsg msg) {
        ChainProcessingContext ctx = msg.getCtx();
        ToDeviceActorMsg inMsg = ctx.getInMsg();
        // 不需要返回的直接return
        if (inMsg.getSessionType() == SessionType.SUB_PUBLISH )
        	return;
        SessionId sid = inMsg.getSessionId();
        ToDeviceSessionActorMsg response;
        if (ctx.getResponse() != null) {
            response = new BasicToDeviceSessionActorMsg(ctx.getResponse(), sid);
        } else {
            response = new BasicToDeviceSessionActorMsg(ctx.getError(), sid);
        }
        sendMsgToSessionActor(response, inMsg.getServerAddress());
    }

    private void sendMsgToSessionActor(ToDeviceSessionActorMsg response, Optional<ServerAddress> sessionAddress) {
       systemContext.getSessionManagerActor().tell(response, ActorRef.noSender());
    }

    private List<AttributeKvEntry> fetchAttributes(String scope) {
        //try {
            //TODO: replace this with async operation. Happens only during actor creation, but is still criticla for performance,
            //return systemContext.getAttributesService().findAll(this.deviceId, scope).get();
        	return systemContext.getAttributesService().findAll(this.deviceId, scope);
        /*} catch (InterruptedException | ExecutionException e) {
            logger.warning("[{}] Failed to fetch attributes for scope: {}", deviceId, scope);
            throw new RuntimeException(e);
        }*/
    }
    
    private List<TelemetryAttributes> fetchTelemetryAttributes(String devId) {
    	List<TelemetryAttributesEntity> t = systemContext.getTelemetryAttributesService().getListByDevId(devId);
    	return DaoUtil.convertDataList(t);
    }
    
    public void processTelemetryAttributesUpdate(ActorContext context, DeviceTelemetryAttributesUpdateNotificationMsg msg) {
    	this.deviceTelemetryAttributes = msg.getDeviceTelemetryAttributes();
    }
    
    // mzh 2017-08-12 代码迁移
    public void processRegisterTimeoutMsg(ActorContext context,RegisterSessionTimeoutMsg msg){
    	// 1、是否注册超时，并且刷新时间超时
    	// 2、在线状态超时
		long expireTime = System.currentTimeMillis() - onlineStateTimeout;

		if ((expireTime > this.session.getFreshTime() && expireTime > lastDataTime))
		{
			String devId = session.getDeviceId();
			if(devId != null &&  !devId.isEmpty()){
	    		Device device = new Device(DeviceId.fromString(devId));
				device.setStatus(OFF_LINE);
				systemContext.getDeviceService().updateStatus(device);
	    		systemContext.getDeviceAuthService().deleteRegisterSessionByDevId(session.getDeviceId());
				logger.warning("did [{}] offline,token [{}],freshTime [{}],latestTime [{}]",
															deviceId.getId(),
															session.getToken(),
															DATE_FORMAT.format(session.getFreshTime()),
															DATE_FORMAT.format(lastDataTime));
				// 将监控状态通知
				String forwardMsg = systemContext.getGson().toJson(new DeviceStatus(UUIDUtils.toUUID(deviceId.getId()),0));
				context.parent().tell(new ForwardMsgRequest("","status",forwardMsg),ActorRef.noSender());
			}
			context.parent().tell(new DeviceTerminationMsg(new DeviceSessionId(deviceId.getId())), ActorRef.noSender());
			context.stop(context.self());
		}else{
			RegisterSessionTimeoutMsg timeoutMsg = new RegisterSessionTimeoutMsg(new DeviceSessionId(deviceId.getId()));
			scheduleMsgWithDelay(context, timeoutMsg, systemContext.getCheckTime());
		}
	}
    
    /**
     * 处理注册请求，更新设备状态，生成响应消息
     * @param msg
     * @return
     */
    private ToDeviceMsg processRegisterRequest(BasicRegisterRequest msg){
    	logger.debug("did [{}] register request is processing.",deviceId);
    	int errno = ResponseBodyCode.SUCCESS.getStatusCode();
    	String error = "";

    	// 更新协议自定义的超时时长
    	long customTimeout = msg.getOnlineTimeout();
		onlineStateTimeout = (customTimeout > 0) ? customTimeout : onlineStateTimeout;

    	session.setDeviceId(deviceId.toString());
		session.setToken(UUID.randomUUID().toString());
		session.setRegisterTime(System.currentTimeMillis());
		session.setFreshTime(session.getRegisterTime());
		session.setStatus(RegisterSession.Status.ONLINE);
		// 更新设备状态
		Device device = new Device(deviceId);
		device.setStatus(ON_LINE);
		for (KvEntry entry : msg.getData()) {
			if (entry.getKey().equals(GATEWAY_KEY)) {
				// 如果存在网关ID，此ID为网关的硬件标识
				Optional<Device> gatewayDevice = systemContext.getDeviceService().findDeviceByHardIdentity(entry.getStrValue().get());
				if (gatewayDevice.isPresent()) {
					device.setGatewayId(gatewayDevice.get().getDevId());
					logger.debug("gateway hardware identity [{}],did [{}]",entry.getStrValue().get(),gatewayDevice.get().getDevId());
				}else {
					logger.error("gateway hardware identity [{}] is invalid",entry.getStrValue().get());
					errno = ResponseBodyCode.INVALID_PARAM.getStatusCode();
					error = GATEWAY_KEY+" is invalid";
				}
			}
		}
		systemContext.getDeviceService().updateStatus(device);
		systemContext.getDeviceAuthService().saveRegisterSession(session);
		logger.info("did [{}] online,time [{}]",deviceId,DATE_FORMAT.format(System.currentTimeMillis()));
		//生成响应消息
		RegisterResponseBodyMsg bodyMsg = new RegisterResponseBodyMsg(errno, error, session.getToken());
		BasicRegisterResponse response = BasicRegisterResponse.onSuccess(msg.getMsgType(), 
										msg.getRequestId(),bodyMsg);
		return response;
    }
    
    /**
     * 设备去注册，更新设备状态，生成响应消息
     * @param msg
     * @return
     */
    private ToDeviceMsg processDeregisterRequest(BasicDeregisterRequest msg){
    	int errno = ResponseBodyCode.SUCCESS.getStatusCode();
    	String error = "";
		String token = msg.getRegisterToken();
		if (session.getToken().equals(token)) {
			session.setStatus(RegisterSession.Status.DEREGISERED);
			session.setToken("");
			// 更新设备状态
			Device device = new Device(deviceId);
			device.setStatus(OFF_LINE);
			systemContext.getDeviceService().updateStatus(device);
			systemContext.getDeviceAuthService().deleteRegisterSessionByDevId(deviceId.toString());
			logger.info("did [{}] deregister request is processed.",deviceId);
		}else{
			errno = ResponseBodyCode.INVALID_TOKEN.getStatusCode();
			error = "token is invalid.";
			logger.warning("did [{}] orginal token [{}] but request token [{}].",deviceId,session.getToken(),token);
		}
		
		DeRegisterResponseBodyMsg bodyMsg = new DeRegisterResponseBodyMsg(errno, error);
		BasicDeRegisterResponse response = BasicDeRegisterResponse.onResponse(msg.getRequestId(), bodyMsg);
		return response;
    }
    
    /**
     * ToDeviceSessionActorMsg封装的有些多余，后续考虑去掉
     * 1，更新session时间
     * 2，处理缓存中的命令列表
     *
     * @param msg
     */
    private ToDeviceMsg processPingRequest(BasicPingRequest msg){
    	logger.debug("did [{}] ping request is processing.",deviceId);
    	int errno = ResponseBodyCode.SUCCESS.getStatusCode();
    	String error = "";
		if (session.getStatus() == RegisterSession.Status.NOT_REGISTERED
				|| session.getStatus() == RegisterSession.Status.DEREGISERED) {
			errno = ResponseBodyCode.CLIENT_ERROR.getStatusCode();
			error = "not registered.";
			logger.debug("did [{}] ping request,but not registered.",deviceId);
		}else {
			session.setFreshTime(msg.getFreshTime());
			session.setStatus(RegisterSession.Status.ONLINE);
		}
		
		PingResponseBodyMsg bodyMsg = new PingResponseBodyMsg(errno, error);
		if (errno == ResponseBodyCode.SUCCESS.getStatusCode()) {
			for(Map.Entry<UUID, CommandMsg> entry : cmdMsgMap.entrySet()) {
				BasicCommandMsg<CommandAwareMsg> cmdMsg = (BasicCommandMsg<CommandAwareMsg>)entry.getValue();
				if(cmdMsg != null) {
					switch (cmdMsg.getCmdType()) {
					case QUERY_REQUEST:
						// 下发给设备消息
						QueryCmdRequestMsg queryCmdMsg = (QueryCmdRequestMsg)cmdMsg.getData();
						bodyMsg.addReportAtrributeData(queryCmdMsg.getHardIdentity());						
						// 通知前台命令下发成功
						queryResponseMsg(cmdMsg,true);
						cmdMsgMap.remove(entry.getKey());
						break;
					case SET_REQUEST:
						SetCmdRequestMsg setCmdMsg = (SetCmdRequestMsg)cmdMsg.getData();
		        		//获取对应设备（网关，网关的子设备，普遍设备）
		        		Optional<Device> deviceOpt = systemContext.getDeviceService().findDeviceByHardIdentity(setCmdMsg.getHardIdentity());
		        		DeviceId devId = new DeviceId(UUIDUtils.toUUID(deviceOpt.get().getDevId()));
						// 更新数据库
						systemContext.getConfigAttributesService().save(devId, setCmdMsg.getAttributes());
						
						// 下发给设备消息
						bodyMsg.addUpdateAttributeData(setCmdMsg.getHardIdentity());
						// 通知前台命令下发超时
						setResponseMsg(cmdMsg,true);
						cmdMsgMap.remove(entry.getKey());
						break;
					case UPGRADE_REQUEST:
                        UpgradeCmdRequestMsg upgradeCmdRequestMsg = (UpgradeCmdRequestMsg) cmdMsg.getData();
                        bodyMsg.addUpgradeGWData(upgradeCmdRequestMsg.getHardIdentity());
                        cmdMsgMap.remove(entry.getKey());
					    break;
					default:
						logger.warning("[{}] CommandMsgType has not support.",cmdMsg.getCmdType());
						break;
					}
				}
			}
		}
		BasicPingResponse response = BasicPingResponse.onResponse(msg.getRequestId(),bodyMsg);
		return response;
    }
    
    public void processCommandMsg(ActorContext contex,CommandMsg msg){	

    	if(sessionType == SessionType.ASYNC){
    		if(session.getStatus() != RegisterSession.Status.ONLINE){
    			logger.error("did [{}] current status is offline.",deviceId);
    			return;
    		}
    		//gatewayId
    		String gatewayId = null;
    		String hardIdentity = null;
    		ConcurrentMap<String, String> attributeMap = null;
    		List<String> attributeList = null;
    		BasicCommandMsg<CommandAwareMsg> cmdMsg = (BasicCommandMsg<CommandAwareMsg>)msg;
    		CommandMsgType cmdMsgType = cmdMsg.getData().getMsgType();
    		
        	if(cmdMsgType == CommandMsgType.SET_REQUEST) {
        		SetCmdRequestMsg setCmdMsg = (SetCmdRequestMsg) cmdMsg.getData();
        		gatewayId = setCmdMsg.getGatewayId();
        		hardIdentity = setCmdMsg.getHardIdentity();
        		attributeMap = setCmdMsg.getAttributes();
        		setResponseMsg(cmdMsg,true);
        		
        		//获取对应设备（网关，网关的子设备，普遍设备）
        		Optional<Device> deviceOpt = systemContext.getDeviceService().findDeviceByHardIdentity(hardIdentity);
        		DeviceId devId = new DeviceId(UUIDUtils.toUUID(deviceOpt.get().getDevId()));
        		// 更新数据库
				systemContext.getConfigAttributesService().save(devId, attributeMap);
        	}
        	else if(cmdMsgType == CommandMsgType.QUERY_REQUEST){
        		QueryCmdRequestMsg queryCmd = (QueryCmdRequestMsg) cmdMsg.getData();
        		hardIdentity = queryCmd.getHardIdentity();
        		gatewayId = queryCmd.getGatewayId();
        		attributeList = queryCmd.getAttributes();
        		//通知前台命令下发成功
    			queryResponseMsg(cmdMsg,true);
        	}
        		
    		//SessionId sessionId = new TcpSessionId(UUIDUtils.toUUID(gatewayId));//网关硬件标识作sessionid
    		ToDeviceRequestMsg deviceReq = new ToDeviceRequestMsg();
    		deviceReq.setCmdMsg(cmdMsg);
    		
    		ToDeviceSessionActorMsg sendMsg = null;
        	sendMsg = new BasicToDeviceSessionActorMsg(deviceReq, sessionId);
        	logger.debug("did [{}] command msg to device,msg [{}]",deviceId,sendMsg);
    		systemContext.getSessionManagerActor().tell(sendMsg, ActorRef.noSender());

    	}
    	else{
			logger.info("did [{}] put message into queue,command msg [{}]",deviceId,msg);
			UUID msgId = UUID.randomUUID();
			cmdMsgMap.put(msgId, msg);
		    // 命令下发超时
		    scheduleMsgWithDelay(contex, new CommandSendTimeoutMsg(msgId), timeout * 1000);
    	}
	}
    
    /**
     * 处理命令会话超时
     * @param msg
     */
    public void processCommandTimeoutMsg(CommandSendTimeoutMsg msg){
        
    	BasicCommandMsg<CommandAwareMsg> cmdMsg = (BasicCommandMsg<CommandAwareMsg>)cmdMsgMap.get(msg.getMsgId());
		if(cmdMsg != null) {
	    	if(System.currentTimeMillis()-cmdMsg.getCreateTime() >= timeout * 1000) {
				logger.info("did [{}] command msg timeout,msg [{}]",deviceId,cmdMsg);
				switch (cmdMsg.getCmdType()) {
				case QUERY_REQUEST:
					// 通知前台命令下发超时
					queryResponseMsg(cmdMsg,false);
					cmdMsgMap.remove(msg.getMsgId());
					break;
				case SET_REQUEST:
					// 通知前台命令下发超时
					setResponseMsg(cmdMsg,false);
					cmdMsgMap.remove(msg.getMsgId());
					break;
				case UPGRADE_REQUEST:
				    //更新设备状态 为下发超时
                    this.systemContext.getUpgradeRecordService().updateLatestRecordStatus(UUIDUtils.toUUID(cmdMsg.getDeviceId().getId()),2);
                    cmdMsgMap.remove(msg.getMsgId());
                    break;
				default:
					logger.warning("CommandMsgType [{}] has not support,msg [{}]",cmdMsg.getCmdType(),cmdMsg);
					break;
				}
	    	}
		}		
    }
    
    /**
     * 通知前台实时查询命令下发是否成功
     * @param msg
     */
    private void queryResponseMsg(BasicCommandMsg<CommandAwareMsg> msg, boolean status){
    	String devId = UUIDUtils.toUUID(msg.getData().getDeviceId().getId());
    	String sessionId = msg.getSessionId();
    	QueryCmdResponseMsg queryCmdMsg = new QueryCmdResponseMsg(new DeviceId(UUIDUtils.toUUID(devId)));
    	if(status) {
			queryCmdMsg.setStatusCode(0);
			queryCmdMsg.setCause("命令下发成功");
    	}
    	else {
			queryCmdMsg.setStatusCode(1);
			queryCmdMsg.setCause("命令下发超时");
    	}
		QueryCmdRequestMsg reqMsg = (QueryCmdRequestMsg)msg.getData();
		List<String> attributeList = reqMsg.getAttributes();
		queryCmdMsg.setAttributes(attributeList);
		
		BasicCommandMsg<QueryCmdResponseMsg> respMsg = new BasicCommandMsg<QueryCmdResponseMsg>(msg.getTenantId(),msg.getDeviceId(),msg.getSessionId(),queryCmdMsg);
		respMsg.setCreateTime(new Date().getTime());
		AbstractCmdMsg.queryRespMsgMap.put(devId + sessionId, respMsg);	
    }
    
    /**
     * 通知前台实时设置命令下发是否成功
     * @param msg
     */
    private void setResponseMsg(BasicCommandMsg<CommandAwareMsg> msg, boolean status){
    	String devId = UUIDUtils.toUUID(msg.getData().getDeviceId().getId());
    	String sessionId = msg.getSessionId();
    	SetCmdResponseMsg setCmdMsg = new SetCmdResponseMsg(new DeviceId(UUIDUtils.toUUID(devId)));
    	if(status) {
	    	setCmdMsg.setStatusCode(0);
			setCmdMsg.setCause("命令下发成功");
    	}
    	else {
	    	setCmdMsg.setStatusCode(1);
			setCmdMsg.setCause("命令下发超时");	
    	}
		SetCmdRequestMsg reqMsg = (SetCmdRequestMsg)msg.getData();
		ConcurrentMap<String, String> attributeMap = reqMsg.getAttributes();
		setCmdMsg.setAttributes(attributeMap);
		
		BasicCommandMsg<SetCmdResponseMsg> respMsg = new BasicCommandMsg<SetCmdResponseMsg>(msg.getTenantId(),msg.getDeviceId(),msg.getSessionId(),setCmdMsg);
		respMsg.setCreateTime( new Date().getTime());
		AbstractCmdMsg.setRespMsgMap.put(devId + sessionId, respMsg);	
    }
}

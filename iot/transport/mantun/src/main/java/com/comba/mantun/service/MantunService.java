package com.comba.mantun.service;

import static com.comba.mantun.message.Constants.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.comba.mantun.session.PlatformSessionCtx;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comba.mantun.rest.RestTemplateFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MantunService {

	@Autowired
	private RestTemplateFactory factory;
	@Autowired
	private AuthorizeService authorizeService;
	@Autowired
	private AuthorizeConfig config;
	@Value("${mantun.enabled:false}")
	private boolean enabled;
	@Value("${mantun.interval.time:30000}")
	private int interval;
	@Value("${mantun.interval.initDelay:10000}")
	private int intervalInitDelay;
	@Value("${mantun.token.timeOut:3600000}")
	private int tokenTimeOut;
	@Value("${mantun.token.initDelay:0}")
	private int tokenInitDelay;
	@Autowired(required = false)
	private SessionMsgProcessor processor;
	@Autowired(required = false)
	private DeviceAuthService deviceAuthService;

	private PlatformSessionCtx sessionCtx;
	private ScheduledExecutorService scheduledThreadPool;

	@PostConstruct
	public void init(){
		if (!enabled){
			log.info("mantun module disabled.");
			return;
		}
		// 初始化会话上下文
		sessionCtx = new PlatformSessionCtx(processor,deviceAuthService,factory.getRestTemplate());
		log.info("mantun module sessionId [{}]",sessionCtx.getSessionId());
		configuration = config;
		//getToken();
		scheduledThreadPool = Executors.newScheduledThreadPool(2);
		scheduledThreadPool.scheduleAtFixedRate(new CheckTask(sessionCtx,factory.getRestTemplate(),config),
							intervalInitDelay,interval, TimeUnit.MILLISECONDS);
		scheduledThreadPool.scheduleAtFixedRate(new RefreshTokenTask(authorizeService),
							tokenInitDelay,tokenTimeOut, TimeUnit.MILLISECONDS);
	}
	
	/*public void getToken(){
		token = authorizeService.getAccessToken();
	}*/

	@PreDestroy
	public void destory(){
		if (scheduledThreadPool != null){
			scheduledThreadPool.shutdown();
		}
	}
}

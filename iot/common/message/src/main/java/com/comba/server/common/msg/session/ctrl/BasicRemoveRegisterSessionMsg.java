/**
 * Copyright (C) 2015 Comba Telecom Systems Holdings Ltd. All rights reserved
 *
 * 本代码版权归京信通信系统控股有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 */
package com.comba.server.common.msg.session.ctrl;

import com.comba.server.common.data.id.SessionId;

/**
 * 【SessionActor->SessionManagerActor通知SessionManagerActor删除注册会话】
 *
 * @version 
 * @author xianhongdong  2017年6月5日 上午9:02:26
 * 
 */
public class BasicRemoveRegisterSessionMsg implements RemoveRegisterSessionMsg {

	private final SessionId sessionId;
	private final String token;
	private final String deviceId;
	
	public BasicRemoveRegisterSessionMsg(SessionId sessionId,String deviceId,String token){
		this.sessionId = sessionId;
		this.token = token;
		this.deviceId = deviceId;
	}
	
	/**
	 * 【获取当前请求的Id】
	 * 
	 * (non-Javadoc)
	 * @see com.comba.server.common.msg.aware.SessionAwareMsg#getSessionId()
	 */
	@Override
	public SessionId getSessionId() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return sessionId;
	}

	@Override
	public String getRegisterSessionToken(){
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return token;
	}

	@Override
	public String getDeviceId() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return deviceId;
	}

}

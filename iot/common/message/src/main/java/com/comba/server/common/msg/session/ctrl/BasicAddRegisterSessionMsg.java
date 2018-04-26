/**
 * Copyright (C) 2015 Comba Telecom Systems Holdings Ltd. All rights reserved
 *
 * 本代码版权归京信通信系统控股有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 */
package com.comba.server.common.msg.session.ctrl;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.data.session.RegisterSession;

/**
 * 【请在此写上此类功能描述文字】
 *
 * @version 
 * @author xianhongdong  2017年6月2日 下午5:19:50
 * 
 */
public class BasicAddRegisterSessionMsg implements AddRegisterSessionMsg {

	private final RegisterSession registerSession;
	private final SessionId sessionId;
	public BasicAddRegisterSessionMsg(SessionId sessionId,RegisterSession registerSession){	
		this.registerSession = registerSession;
		this.sessionId = sessionId;
	}
	/**
	 * 【获取请求的Id】
	 * 
	 * (non-Javadoc)
	 * @see com.comba.server.common.msg.aware.SessionAwareMsg#getSessionId()
	 */
	@Override
	public SessionId getSessionId() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return sessionId;
	}

	/**
	 * 【获取注册会话对象】
	 * 
	 * (non-Javadoc)
	 * @see com.comba.server.common.msg.session.ctrl.AddRegisterSessionMsg#getRegisterSession()
	 */
	@Override
	public RegisterSession getRegisterSession() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return registerSession;
	}

}

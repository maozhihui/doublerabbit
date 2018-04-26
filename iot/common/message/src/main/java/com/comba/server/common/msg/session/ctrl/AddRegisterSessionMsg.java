/**
 * Copyright (C) 2015 Comba Telecom Systems Holdings Ltd. All rights reserved
 *
 * 本代码版权归京信通信系统控股有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 */
package com.comba.server.common.msg.session.ctrl;

import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.common.msg.session.SessionCtrlMsg;

/**
 * 【sessionActor->sessionMangerActor消息，告知其需要将注册会话添加到缓存】
 *
 * @version 
 * @author xianhongdong  2017年6月2日 下午5:18:26
 * 
 */
public interface AddRegisterSessionMsg extends SessionCtrlMsg {
	
	/**
	 * 【获取注册会话对象】
	 * 
	 * @author xianhongdong 2017年6月5日
	 * @return
	 */
	RegisterSession getRegisterSession();
}

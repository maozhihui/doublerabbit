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
package com.comba.server.common.transport.auth;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.security.DeviceCredentialsFilter;
import com.comba.server.common.data.session.RegisterSession;

import java.util.Optional;

/**
 * 设备鉴权服务接口
 * @author maozhihui
 *
 */
public interface DeviceAuthService {

    DeviceAuthResult process(DeviceCredentialsFilter credentials);

    Optional<Device> findDeviceById(DeviceId deviceId);

    /**
     * 根据硬件标识查找设备
     * @param hardIdentity 
     * @return
     */
    Optional<Device> findDeviceByHardIdentity(String hardIdentity);
    
    /** 根据设备ID获取设备的token信息
	 * @param devId
	 * @return
	 */
	RegisterSession getDeviceRegisterSession(String devId);
	
	/**将注册会话信息保存到数据库中
	 * @param session
	 * @return
	 */
	boolean saveRegisterSession(RegisterSession session);
	
	/**
	 * 根据设备ID删除注册会话
	 * @param devId 平台的设备ID
	 * @return
	 */
	Integer deleteRegisterSessionByDevId(String devId);
	
	/**
	 * 获取设备的注册token
	 * @param devId
	 * @return
	 */
	String getRegisterToken(String devId);
}

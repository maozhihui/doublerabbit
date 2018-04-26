package com.comba.server.dao.device;

import com.comba.server.common.data.DeviceToken;

/**
 * @Description Device Token服务接口
 * @author maozhihui
 * @date 2017年8月30日 下午3:37:46
 */
public interface DeviceTokenService {
	
	/**
	 * @Description 不带“-”的设备ID
	 * @param deviceId
	 * @return DeviceToken
	 */
	DeviceToken findTokenByDevId(String deviceId);
	
	/**
	 * @Description 保存并强制写入数据库
	 * @param deviceToken
	 * @return DeviceToken
	 */
	DeviceToken saveAndFlush(DeviceToken deviceToken);
	
	/**
	 * @Description 根据devId删除记录
	 * @param deviceId 不带“-”
	 * @return Integer 返回数据库受影响行数
	 */
	Integer deleteTokenByDevId(String deviceId);
}

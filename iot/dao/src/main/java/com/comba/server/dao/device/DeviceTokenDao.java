package com.comba.server.dao.device;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.comba.server.dao.model.device.DeviceTokenEntity;

/**
 * @author xianhongdong
 *
 */
public interface DeviceTokenDao extends JpaRepository<DeviceTokenEntity, String>{

	/**
	 * @Description 根据deviceId查找
	 * @param devId
	 * @return
	 */
	//List<DeviceTokenEntity> findByDevId(String devId);
	
	/**
	 * @Description 根据deviceId查找对应的DeviceTokenEntity
	 * @param devId
	 * @return DeviceTokenEntity
	 */
	DeviceTokenEntity findOneByDevId(String devId);
	
	/**
	 * 
	 * @Description 根据deviceId删除对应的DeviceTokenEntity
	 * @param devId
	 * @return Integer
	 */
	@Transactional
	@Modifying
	Integer deleteByDevId(String devId);
}

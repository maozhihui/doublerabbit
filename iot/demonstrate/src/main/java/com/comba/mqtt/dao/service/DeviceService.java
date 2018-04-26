package com.comba.mqtt.dao.service;

import java.util.List;
import java.util.Optional;

import com.comba.mqtt.dao.model.DeviceEntity;

/**
 * 
 * @author maozhihui
 *
 */
public interface DeviceService {
	
	Optional<DeviceEntity> findByDevEui(String devEui);
	
	List<DeviceEntity> findByCategoryId(int categoryId);
}

package com.comba.mqtt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.mqtt.dao.model.DeviceEntity;

public interface DeviceDao extends JpaRepository<DeviceEntity, Integer> {

	DeviceEntity findByDevEui(String devEui);
	
	List<DeviceEntity> findByCategoryId(int categoryId);
}

package com.comba.mqtt.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.mqtt.dao.model.DeviceDataEntity;

public interface DeviceDataDao extends JpaRepository<DeviceDataEntity, Integer> {

	List<DeviceDataEntity> findByDevIdAndUpdateTimeBetween(int devId,Date startTime,Date endTime);
	
}

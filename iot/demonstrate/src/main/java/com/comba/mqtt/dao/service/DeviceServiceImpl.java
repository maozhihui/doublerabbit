package com.comba.mqtt.dao.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.mqtt.dao.DeviceDao;
import com.comba.mqtt.dao.model.DeviceEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public Optional<DeviceEntity> findByDevEui(String devEui) {
		if (StringHelper.isEmpty(devEui))
			return Optional.ofNullable(null);
		DeviceEntity entity = deviceDao.findByDevEui(devEui);
		if (entity == null) {
			log.warn("can not find devEUI [{}]",devEui);
			return Optional.ofNullable(null);
		}
		return Optional.of(entity);
	}

	@Override
	public List<DeviceEntity> findByCategoryId(int categoryId) {
		return deviceDao.findByCategoryId(categoryId);
	}

}

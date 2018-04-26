package com.comba.mqtt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.comba.mqtt.dao.service.DeviceDataService;
import com.comba.mqtt.dao.service.DeviceService;
import com.comba.mqtt.dao.service.LatestDataService;

import lombok.Getter;

@Component
public class MqttServiceContext {

	@Autowired
	@Getter private DeviceService deviceService;
	
	@Autowired
	@Getter private DeviceDataService deviceDataService;
	
	@Autowired
	@Getter private LatestDataService latestDataService;
	
	@Value("${temperture.max}")
	@Getter private int maxTemperture;
	
	@Value("${humidity.max}")
	@Getter private int maxHumidity;
	
	@Value("${voltage.min}")
	@Getter private double minVoltage;
}

package com.comba.mqtt.dao.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.junit4.SpringRunner;

import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest()
//@TestPropertySource(locations = {"classpath:application-test.yml"})
@ActiveProfiles("test")
public class AbstractDaoTest {
	
	@Autowired
	protected DeviceDataService deviceDataService;
	
	@Autowired
	protected LatestDataService latestDataService;
}

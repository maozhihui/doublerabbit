package com.comba.mqtt.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.comba.mqtt.message.ResponseBean;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeviceOpControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testPtzStart() {
		String url = "http://localhost:8080/device/ptz/start?accessToken=at.6jpf2fkx6u8m8czjaq2pw0ic2w9gy8bx-5q4m9cqyy5-0bqpjrd-uzpsavhzu&deviceSerial=110673568&channelNo=1&direction=1&speed=1";
		ResponseBean responseBean = this.restTemplate.postForObject(url, null ,ResponseBean.class);
		if (responseBean != null) {
			System.out.println("code=" + responseBean.getCode() + "msg=" + responseBean.getMsg());
		}
		
	}
	
	@Test
	public void testPtzStop() {
		String url = "http://localhost:8080/device/ptz/stop?accessToken=at.6jpf2fkx6u8m8czjaq2pw0ic2w9gy8bx-5q4m9cqyy5-0bqpjrd-uzpsavhzu&deviceSerial=110673568&channelNo=1&direction=1&speed=1";
		ResponseBean responseBean = this.restTemplate.postForObject(url, null ,ResponseBean.class);
		if (responseBean != null) {
			System.out.println("code=" + responseBean.getCode() + "msg=" + responseBean.getMsg());
		}
		
	}

}

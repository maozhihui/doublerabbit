package com.comba.mqtt.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.comba.mqtt.message.ResponseBean;
import com.comba.mqtt.service.auth.AuthService;
import com.comba.mqtt.service.rest.RestConfiguration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author maozhihui
 * @date 2017年11月6日 上午11:16:07
 */
@Slf4j
@RestController
public class DeviceOpController {

	public static volatile String accessToken = "";
	
	@Autowired
	RestConfiguration configurationBean;
	
	@Autowired
	AuthService authService;
	
	@Value("${base.url}")
	private String baseUrl;

	@Value("${uri.ptz.start}")
	private String stratUri;
	
	@Value("${uri.ptz.stop}")
	private String stopUri;
	
	/**
	 * 利用云台协议进行摄像头控制
	 * @return
	 */
	@PostMapping(value = "${uri.ptz.start}")
	public ResponseBean ptzStart(PtzRequest request){
		log.info("ptzStart request param [{}]",request);
		StringBuilder requestUrl = new StringBuilder(baseUrl);
		requestUrl.append(stratUri).append("?").append(request);
		RestTemplate restTemplate = configurationBean.restTemplate(configurationBean.httpRequestFactory());
		ResponseBean response = null;
		try {
			response = restTemplate.postForObject(requestUrl.toString(), null, ResponseBean.class);
		} catch (RestClientException e) {
			log.error("request server failed,cause [{}]",e.getMessage());
		}
		if (response.getCode().equals("200")) {
			log.info("operate device success [{}]",response.getMsg());
		}else {
			log.error("operate device failed code [{}],msg [{}]",response.getCode(),response.getMsg());
		}
		return response;
	}
	
	@PostMapping(value = "${uri.ptz.stop}")
	public ResponseBean ptzStop(PtzRequest request){
		log.info("request param [{}]",request);
		StringBuilder requestUrl = new StringBuilder(baseUrl);
		requestUrl.append(stratUri).append("?").append(request);
		RestTemplate restTemplate = configurationBean.restTemplate(configurationBean.httpRequestFactory());
		ResponseBean response = null;
		try {
			response = restTemplate.postForObject(requestUrl.toString(), null, ResponseBean.class);
		} catch (RestClientException e) {
			log.error("request server failed,cause [{}]",e.getMessage());
		}
		if (response.getCode().equals("200")) {
			log.info("operate device success [{}]",response.getMsg());
		}else {
			log.error("operate device failed code [{}],msg [{}]",response.getCode(),response.getMsg());
		}
		return response;
	}
	
	/**
	 * 前端请求token
	 * @return
	 */
	@GetMapping(value = "${uri.token}")
	public String getToken(){
		if (StringUtils.isBlank(accessToken)) {
			accessToken = authService.getToken();
			log.info("from [{}] get token [{}]",baseUrl,accessToken);
		}
		return accessToken;
	}
	
	@Data
	public static class PtzRequest{
		private String accessToken;
		private String deviceSerial;
		private Integer channelNo;
		private Integer direction;
		private Integer speed;
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(accessToken)) {
				sb.append("accessToken=").append(accessToken);
			}
			if (StringUtils.isNotBlank(deviceSerial)) {
				sb.append("&").append("deviceSerial=").append(deviceSerial);
			}
			if (channelNo != null) {
				sb.append("&").append("channelNo=").append(channelNo);
			}
			if (direction != null) {
				sb.append("&").append("direction=").append(direction);
			}
			if (speed != null) {
				sb.append("&").append("speed=").append(speed);
			}
			return sb.toString();
		}
	}
}

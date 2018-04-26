package com.comba.mantun.rest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Service
public class RestTemplateFactory {

	@Resource(name = "mantunConfig")
	private RestConfiguration restConfiguration;
	
	@Getter private RestTemplate restTemplate;
	
	@PostConstruct
	public void init(){
		restTemplate = restConfiguration.restTemplate(restConfiguration.httpRequestFactory());
	}
}

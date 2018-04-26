package com.comba.mantun.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AuthorizeConfig {

	@Value("${mantun.api.clientId}")
	private String clientId;
	@Value("${mantun.api.appSecret}")
	private String appSecret;
	@Value("${mantun.api.redirectUri}")
	private String redirectUri;
	@Value("${mantun.api.uame}")
	private String userName;
	@Value("${mantun.api.passwd}")
	private String password;
	@Value("${mantun.api.projectCode}")
	private String projectCode;
	@Value("${mantun.api.authorizeUri}")
	private String authorizeUri;
	@Value("${mantun.api.authorizeVerify}")
	private String authorizeVerify;
	@Value("${mantun.api.tokenUri}")
	private String tokenUri;
	@Value("${mantun.api.apiUri}")
	private String apiUri;
}

package com.comba.mantun.service;

import static com.comba.mantun.message.Constants.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.comba.mantun.message.AccessToken;
import com.comba.mantun.message.AuthverifyParam;
import com.comba.mantun.message.ResponseBean;
import com.comba.mantun.message.TokenParam;
import com.comba.mantun.rest.RestTemplateFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthorizeService {
	
	@Autowired
	private RestTemplateFactory factory;

	@Autowired
	private AuthorizeConfig config;

	// http://open.snd02.com/oauth/authorize.as?client_id=O000000034&response_type=code&redirect_uri=http://open.snd02.com/demo.jsp
	private String getCode() {
		StringBuilder reqUri = new StringBuilder(config.getAuthorizeVerify());
		AuthverifyParam authverify = new AuthverifyParam(config.getClientId(), config.getRedirectUri(),
				config.getUserName(), config.getPassword());
		try {
			ResponseEntity<String> res = factory.getRestTemplate().exchange(reqUri.toString(), HttpMethod.POST,
					getHttpEntity(authverify.toString()), String.class);
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(res.getBody());
			if (element.isJsonObject()) {
				return element.getAsJsonObject().get("code").getAsString();
			} else {
				log.error("get code failed.");
				return "";
			}
		}catch (Exception e){
			log.error("mantun get code failed by exception [{}]",e.getMessage());
		}
		return "";
	}

	private HttpEntity<?> getHttpEntity(String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
		return entity;
	}

	public String getAccessToken() {
		String code = getCode();
		if (StringUtils.isBlank(code)) {
			return "";
		}
		StringBuilder reqUri = new StringBuilder(config.getTokenUri());
		TokenParam tokenParam = new TokenParam(config.getClientId(), config.getAppSecret(),ACCESS_TOKEN_TYPE, config.getRedirectUri(),
				code);
		try {
			ResponseEntity<String> res = factory.getRestTemplate().exchange(reqUri.toString(), HttpMethod.POST,
					getHttpEntity(tokenParam.toString()), String.class);
			ResponseBean<AccessToken> responseBean = GSON.fromJson(res.getBody(), new TypeToken<ResponseBean<AccessToken>>(){}.getType());
			return responseBean.getData().getAccessToken();
		}catch (Exception e){
			log.error("mantun getAccessToken failed by cause [{}]",e.getMessage());
		}
		return "";
	}

}

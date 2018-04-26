package com.comba.mqtt.service.auth;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.comba.mqtt.message.ResponseBean;
import com.comba.mqtt.service.rest.RestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取token，进行鉴权验证处理
 * @author maozhihui
 * @date 2017年11月3日 下午6:09:29
 */
@Slf4j
@Service
public class AuthService {
	
	@Autowired
	RestConfiguration configurationBean;
	
	@Value("${base.url}${uri.token}")
	private String tokenBaseUrl;
	
	@Value("${auth.key}")
	private String appKey;
	
	@Value("${auth.secret}")
	private String secret;
	
	private String tokenUrl;
	
	public String getToken(){
		//log.info("tokenUrl=" + tokenUrl);
		RestTemplate restTemplate = configurationBean.restTemplate(configurationBean.httpRequestFactory());
		if (StringUtils.isBlank(tokenUrl)) {
			String postDataStr = new PostData(appKey, secret).toString();
			log.info("postDataStr [{}]", postDataStr);
			StringBuilder sb = new StringBuilder(tokenBaseUrl);
			sb.append("?").append(postDataStr);
			tokenUrl = sb.toString();
			log.info("tokenUrl [{}]",tokenUrl);
		}
		
		try {
			ResponseBean res = restTemplate.postForObject(tokenUrl, null, ResponseBean.class);
			Map<String, Object> data = (Map<String, Object>)res.getData();
			log.info("get token code [{}],msg [{}]",res.getCode(),res.getMsg());
			if (data != null) {
				return data.get("accessToken").toString();
			}
			/*String res = restTemplate.postForObject(tokenUrl, null, String.class);
			if (res != null) {
				log.info("token response [{}]",res);
				return res;
			}*/
		} catch (RestClientException e) {
			log.error("get token from server failed,cause [{}]",e.getMessage());
		}
		return "";
	}
	
	@Data
	public static class TokenData{
		private String accessToken;
		private long expireTime;
		
	}
	
	@Data
	@AllArgsConstructor
	public static class PostData{
		private String appKey;
		private String appSecret;
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("appKey=").append(this.appKey)
				.append("&appSecret=").append(this.appSecret);
			return sb.toString();
		}
	}
}

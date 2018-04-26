package com.comba.mantun.message;

import com.comba.mantun.util.MD5Util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author maozhihui
 * @date 2017年12月14日 下午3:01:30
 */
@Slf4j
@Data
public class TokenParam {

	private static final String REFRESH_TOKEN_TYPE = "refresh_token";
	
	private String client_id;
	private String client_secret;
	private String app_secret;
	private String grant_type;
	private String redirect_uri;
	private String code;
	private String refresh_token;
	
	public TokenParam(String clientId,String appSecret,String grantType,String redirectUri,String code){
		this.client_id = clientId;
		this.app_secret = appSecret;
		this.redirect_uri = redirectUri;
		this.code = code;
		this.grant_type = grantType;
	}
	
	public TokenParam(String clientId,String appSecret,String grantType,String redirectUri,String code,String refreshToken){
		this(clientId, appSecret, grantType, redirectUri, code);
		this.refresh_token = refreshToken;
	}
	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (grant_type.equals(REFRESH_TOKEN_TYPE)) {
			sb.append(client_id).append(grant_type).append(redirect_uri)
						.append(refresh_token).append(app_secret);
		}else {
			sb.append(client_id).append(grant_type).append(redirect_uri)
						.append(code).append(app_secret);
		}
		client_secret = MD5Util.MD5(sb.toString());
		sb.setLength(0);
		sb.append("client_id=").append(client_id)
			.append("&client_secret=").append(client_secret)
			.append("&grant_type=").append(grant_type)
			.append("&redirect_uri=").append(redirect_uri)
			.append("&code=").append(code);
		return sb.toString();
	}
}

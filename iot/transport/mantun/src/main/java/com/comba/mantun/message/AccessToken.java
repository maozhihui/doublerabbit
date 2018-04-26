package com.comba.mantun.message;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年12月15日 下午1:01:25
 */
@Data
public class AccessToken {

	private String tokenType;
	private String accessToken;
	private int expiresIn;
	private String refreshToken;
}

package com.comba.server.common.msg.core;

/**
 * 注册响应消息body内容
 * @author maozhihui
 *
 */
public class RegisterResponseBodyMsg extends ResponseBodyMsg {

	private static final long serialVersionUID = 7940224830545728577L;

	private final String token;
	
	public RegisterResponseBodyMsg(int errno,String error,String token){
		super(errno, error);
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
}

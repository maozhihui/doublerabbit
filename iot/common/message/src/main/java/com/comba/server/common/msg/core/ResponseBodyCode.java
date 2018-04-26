package com.comba.server.common.msg.core;

/**
 * 定义响应消息内容中的响应码
 * @author maozhihui
 *
 */
public enum ResponseBodyCode {

	SUCCESS(200),CLIENT_ERROR(400),SERVER_ERROR(500),
	DEVICE_ID_NOT_EXISTS(401),//deviceId不存在
	PASSWORD_ERROR(402),//密码错误 
	INVALID_TOKEN(403),//Token非法
	INVALID_PARAM(404),//参数错误 
	SESSION_EXPIRE(405),//会话失效,需要重新注册
	SERVER_TIMEROUT(504),//服务端处理超时
	SERVER_PROCESS_ERROR(502);//服务端处理发生错误

	private final int statusCode;
	
	ResponseBodyCode() {
		this(0);
	}
	
	ResponseBodyCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public int getStatusCode(){
		return statusCode;
	}
}

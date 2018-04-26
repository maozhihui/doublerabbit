package com.comba.server.common.msg.core;

import java.io.Serializable;

/**
 * 响应内容基类
 * @author maozhihui
 *
 */
public abstract class ResponseBodyMsg implements Serializable {
	private final int errno;
	private final String error;
	
	public ResponseBodyMsg(int errno,String error){
		this.errno = errno;
		this.error = error;
	}

	public int getErrno() {
		return errno;
	}

	public String getError() {
		return error;
	}
	
}

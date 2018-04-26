package com.comba.web.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 异常处理
 * @author wengzhonghui
 *
 */
public class ControllerException extends RuntimeException{
	private static final long serialVersionUID = 2239556271013777613L;

	public ControllerException(String message) {
		super(message);
	}

	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public String getMessage() {
		try {
			return String.format("{ServiceIp:\"%s\",Message:\"%s\"}",
					new Object[] { InetAddress.getLocalHost().getHostAddress(),
							super.getMessage() });
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return String.format("{ServiceIp:\"%s\",Message:\"%s\"}",
					new Object[] { "获取服务器IP失败",
					super.getMessage() });
		}
	}

	public String toString() {
		return getMessage();
	}

	
}

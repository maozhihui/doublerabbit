package com.comba.web.response;

import java.util.Map;

/**
 * 前端异步获取数据时，返回的公用类,用于标准线化
 * @author wengzhonghui
 *
 */
public class ResponseBean {

	private String flag;//是否成功标识
	//web中常用向前端发送的成功失败信息
	public final static String SUCCESS = "success";//标识请求成功
	public final static String FAIL = "fail";//标识请求失败
	
	//需要向前台传递的消息
	private String message;
	//向前面传递的异常信息
	private String exception;
	//需要返回的其他关联信息
	private Map<String,Object> relateData;
	
	public ResponseBean() {
	    this.flag = FAIL;
	}
	
	public ResponseBean(String flag) {
		super();
		this.flag = flag;
	}
	
	public ResponseBean(String flag, String message) {
		super();
		this.flag = flag;
		this.message = message;
	}

	public ResponseBean(String flag, String message, Map<String, Object> relateData) {
		super();
		this.flag = flag;
		this.message = message;
		this.relateData = relateData;
	}
	public void setFlag_success() {
		this.flag = SUCCESS;
	}
	public void setFlag_fail() {
		this.flag = FAIL;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public Map<String, Object> getRelateData() {
		return relateData;
	}
	public void setRelateData(Map<String, Object> relateData) {
		this.relateData = relateData;
	}
	public static String getSuccess() {
		return SUCCESS;
	}
	public static String getFail() {
		return FAIL;
	}
	
}

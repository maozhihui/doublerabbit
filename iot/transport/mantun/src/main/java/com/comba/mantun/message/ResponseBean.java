package com.comba.mantun.message;

import lombok.Data;

/**
 * HTTP请求实体类
 * @author maozhihui
 * @date 2017年12月15日 下午12:59:10
 * @param <T>
 */
@Data
public class ResponseBean<T> {

	private String code;
	private boolean success;
	private String message;
	private T data;
}

package com.comba.web.tablib;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.annotation.Configuration;

import com.comba.web.security.SpringSecurityUtils;






/**
 * 权限控制标签
 * @author wengzhonghui
 *
 */
@Configuration
public class AuthorizeTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String code;

	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public int doStartTag() {
		// 判断是否拥有该权限编码，超级管理员不做权限限制
		if (SpringSecurityUtils.hasPriCode(code) || SpringSecurityUtils.isSupperAdmin()) {
			return EVAL_BODY_INCLUDE;
		}
//		TODO 暂时注释 return this.SKIP_BODY;
		return EVAL_BODY_INCLUDE;
	}
	
	
}
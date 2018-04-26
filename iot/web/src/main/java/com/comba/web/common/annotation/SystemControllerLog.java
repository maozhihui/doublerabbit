package com.comba.web.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




/**
 * 自定义注释拦截器：拦截Controller
 * 在Controller方法上面添加注解，调用Controller方法时就能进入该切面方法
 * 如：	@RequestMapping("authorities_edit")
		@SystemControllerLog(description = "编辑资源权限")    
	public String roleEdit(String authorityId, 
 * 
 * @author wengzhonghui
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface SystemControllerLog {    
    String description()  default "";    
}    

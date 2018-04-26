package com.comba.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;


import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * 资源权限过滤
 *
 * @version 
 * @author wengzhonghui  2017年6月14日 下午5:22:37
 * 
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource
{


	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	
	public MyInvocationSecurityMetadataSourceService()
	{
		loadResourceDefine();
	}

	private void loadResourceDefine()
	{
		
		resourceMap = null;// TODO TEST
	}

	
	public Collection<ConfigAttribute> getAllConfigAttributes()
	{

		return null;
	}

	private static String[] ignoreURLs = null;
	static{
		ignoreURLs = new String[]{"/lock/getLastActionTime","/license/licenseAlarm"};
	}
	
	// 根据URL，找到相关的权限配置。
	
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException{
		
		//TODO 对权限URL暂时不做处理
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String requestUri = filterInvocation.getRequestUrl();
		//记录最后操作时间
		boolean isLogTime = true;
		for (String uri : ignoreURLs) {
			if (requestUri.indexOf(uri) >= 0) {
				isLogTime = false;
			}
		}
		return null;
		
		
//		if(SpringSecurityUtils.isSupperAdmin()){//如果是超级管理员，则拥有所有权限
//			return null;
//		}
//		// object 是一个URL，被用户请求的url。
//		FilterInvocation filterInvocation = (FilterInvocation) object;
//		if(filterInvocation.getFullRequestUrl()!=null ){
//			String url = filterInvocation.getFullRequestUrl();
//			if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".woff")|| url.endsWith(".gif")
//					|| url.endsWith(".png")|| url.endsWith(".jpg")){
//				return null;
//			}
//		}
//		Iterator<String> ite = resourceMap.keySet().iterator();
//		while (ite.hasNext()) {
//		    String requestURL = ite.next();
//		    RequestMatcher requestMatcher = new AntPathRequestMatcher(requestURL);
//		    if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
//		        return resourceMap.get(requestURL);
//		    }
//		}
//		return null;
	}

	
	public boolean supports(Class<?> arg0)
	{

		return true;
	}

}
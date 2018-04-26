package com.comba.web.security;



import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor  implements Filter
{
	@Autowired
    private FilterInvocationSecurityMetadataSource myInvocationSecurityMetadataSourceService;
	
	@Autowired
	private CustomAccessDecisionManager myAccessDecisionManager;
	
	@PostConstruct
	public void init(){
		super.setAccessDecisionManager(myAccessDecisionManager);
	}
	
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException{
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    MyHttpServletRequest myHttpServletRequest = new MyHttpServletRequest(httpServletRequest);
        FilterInvocation fi = new FilterInvocation( myHttpServletRequest, response, chain );
		invoke(fi);
		
	}

	
	public Class<? extends Object> getSecureObjectClass(){
		return FilterInvocation.class;
	}

	
	public void invoke( FilterInvocation fi ) throws IOException, ServletException{
		InterceptorStatusToken  token = super.beforeInvocation(fi);
		try{
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}finally{
			super.afterInvocation(token, null);
		}
		
	}
		
	
	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource(){
		return this.myInvocationSecurityMetadataSourceService;
	}
	
	public void destroy(){
	}
	public void init( FilterConfig filterconfig ) throws ServletException{
	}	

}

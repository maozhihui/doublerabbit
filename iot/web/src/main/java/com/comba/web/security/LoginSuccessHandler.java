package com.comba.web.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comba.web.spring.SessionCounter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.comba.server.common.data.AuditLog;
import com.comba.server.dao.auditLog.consumer.AuditLogQueue;
import com.comba.web.utils.CommonUtil;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{  
    
    @Override  
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication) throws IOException,  
            ServletException {  
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
    	SpringSecurityUserAuth userDetails = (SpringSecurityUserAuth)authentication.getPrincipal();  
    	String logDescription = "用户登陆【"+userDetails.getUsername()+"】";
    	//记录登陆日志
    	AuditLog log = new AuditLog();
        log.setDescription(logDescription);    
        log.setType(AuditLog.TYPE_LOGIN_LOG);    
        log.setRequestIp(CommonUtil.getIpAddress(request));    
        log.setCreateBy(SpringSecurityUtils.getCurrentUserDisplayName());    
        log.setCreateDate(new Date());
        if(SpringSecurityUtils.getCurrentUserId()!=null){
        	log.setUserId(SpringSecurityUtils.getCurrentUserId());
        }
        AuditLogQueue.addSystemLog(log);
        SessionCounter.saveSession(request,userDetails.getUsername());
        super.onAuthenticationSuccess(request, response, authentication);  
    }  
    
}


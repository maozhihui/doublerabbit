package com.comba.web.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.comba.server.common.data.AuditLog;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.auditLog.consumer.AuditLogQueue;
import com.comba.server.dao.common.utils.IdGenerator;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonUtil;



/**
 * 日志审计 切点类
 * @author wengzhonghui
 *
 */
@Aspect    
@Component
public class AuditLogAspect
{
	 private  AuditLogQueue auditLogQueue;
	//本地异常日志记录对象    
     private  static  final Logger logger = Logger.getLogger(AuditLogAspect.class); 
     
     //Controller层切点    
     @Pointcut("@annotation(com.comba.web.common.annotation.SystemControllerLog)")    
     public  void controllerAspect() {    
     } 
     
//     @Before("controllerAspect()")    
     @After("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) { //@ TODO 异步线程处理 
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	 //读取session中的用户    
    	 //请求的IP    
    	 String ip = CommonUtil.getIpAddress(request);
    	//获取用户请求方法的参数并序列化为JSON格式字符串    
         String params = "";    
          if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
              for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                 params += JSONUtils.valueToString(joinPoint.getArgs()[i]) + ";";    
             }    
         }
	     try {    
	    	 String logDescription = "";
	    	 if(request.getAttribute(Constants.AOP_LOG_DESCRIPTION)!=null){
	    		 logDescription = request.getAttribute(Constants.AOP_LOG_DESCRIPTION) + "";
	    	 }else{
	    		 logDescription = getControllerMethodDescription(joinPoint);
	    	 }
	        //*========数据库日志=========*//    
	        AuditLog log = new AuditLog();
	        log.setDescription(logDescription);    
	        log.setType(AuditLog.TYPE_USER_OPRATION_LOG);    
	        log.setRequestIp(ip);    
	        log.setCreateBy(SpringSecurityUtils.getCurrentUserDisplayName());    
	        log.setCreateDate(new Date());
	        if(SpringSecurityUtils.getCurrentUserId()!=null){
	        	log.setUserId(SpringSecurityUtils.getCurrentUserId());
	        }
	        //保存数据库    
	        auditLogQueue.add(log);   
	    }  catch (Exception e) {    
	        //记录本地异常日志    
	        logger.error("==前置通知异常==",e);    
	    }    
     }
     
     
	

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception
	{
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length)
				{
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
	@Resource
	public void setAuditLogQueue(AuditLogQueue auditLogQueue) {
		this.auditLogQueue = auditLogQueue;
	}
	
	
	
	

}

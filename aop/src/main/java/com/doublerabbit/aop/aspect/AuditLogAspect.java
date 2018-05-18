package com.doublerabbit.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.doublerabbit.aop.annotation.SystemLog;

import net.sf.json.util.JSONUtils;

/**
 * 日志审计切面类
 * 当使用@AfterThrowing注解时，能够捕获到异常，但不能完全处理异常，仍然会抛给上一级调用者
 * @author maozhihui
 * @date 2018年5月18日 下午3:04:30
 */
@Aspect
@Component
public class AuditLogAspect {

	@Pointcut("@annotation(com.doublerabbit.aop.annotation.SystemLog)")
	public void controllerAspect() {
	}

	/**
	 * 当注解该方法执行前执行的操作
	 */
	@Before("controllerAspect()")
	public void doBefore(){
		System.out.println("execute aspect before function====");
	}
	
	/**
	 * 当注解该方法执行完成后执行的操作
	 * @param joinPoint
	 */
	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JSONUtils.valueToString(joinPoint.getArgs()[i]) + ";";
			}
		}
		System.out.println("request params:" + params);
		try {
			String logDescription = getAnnotationContent(joinPoint);
			System.out.println("logDescription:" + logDescription);
		} catch (Exception e) {
			// 记录本地异常日志
			System.err.println("==前置通知异常==:" + e.getMessage());
		}
	}

	/**
	 * 获取注解方法执行过程中未捕获而抛出的异常
	 * @param te
	 */
	@AfterThrowing(value = "controllerAspect()",throwing = "te")
	public void doException(Throwable te){
		System.out.println("cause exception : " + te.getMessage());
	}
	
	/**
	 * 获取注解方法最终的返回值信息
	 * @param ret
	 */
	@AfterReturning(value = "controllerAspect()",returning = "ret")
	public void doGetReturnValue(Object ret){
		System.out.println("get function return value :" + ret);
	}
	
	/**
	 * 获取注解中对方法的描述信息
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getAnnotationContent(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemLog.class).description();
					break;
				}
			}
		}
		return description;
	}

}

package cn.etcom.web.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.etcom.annotation.LogControllerAnnotation;
import cn.etcom.entity.SystemLog;
import cn.etcom.entity.model.ResponseObject;
import cn.etcom.service.log.SystemLogService;
import cn.etcom.util.enums.ActionEnum;
import cn.etcom.util.log.LoggerUtils;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Web层日志切面
 *
 */
@Aspect
@Order(1)
@Component
public class WebLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	ThreadLocal<Long> startTime = new ThreadLocal<>();
	@Autowired
	private SystemLogService systemLogService;
	/***
	 * 定义controller切入点拦截规则，拦截LogControllerAnnotation注解的方法
	 */
	@Pointcut("@annotation(com.winterchen.conf.annotation.LogControllerAnnotation)")
	public void controllerAspect() {
	}

	/***
	 * 拦截控制层的操作日志
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public ResponseObject recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		SystemLog systemLog = SystemLog.builder().build();
		Object proceed = null;
		// 获取session中的用户
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.getSession().getAttribute("user");
		systemLog.setUserid("admin");
		// 获取请求的ip
		String ip = LoggerUtils.getCliectIp(request);
		systemLog.setRequestip(ip);
		// 获取执行的方法名
		systemLog.setActionmethod(joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		// 获取方法执行前时间
		Date date = new Date();
		systemLog.setActiondate(date)
		         .setUrl(request.getRequestURL()
		         .toString())
		         .setHttpmethod(request.getMethod());
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + LoggerUtils.getCliectIp(request));
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));				
		proceed = joinPoint.proceed();
		// 提取controller中ResponseObject的属性
		ResponseObject result = null;
		try {
			result= (ResponseObject) proceed;
		}
		catch(Exception e) {
			result =   ResponseObject.builder().build();
		}
		if (result.getReturnCode()==0) {
			// 设置操作信息
			systemLog.setType("1");
		} else if(result.getReturnCode()==null){
			systemLog.setType("3");
		}
		else{
			systemLog.setType("2");
			systemLog.setExceptioncode(result.getReturnCode()+"");
			systemLog.setExceptiondetail(result.getReturnMsg());
		}
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + proceed);
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
		// 获取执行方法的注解内容
		systemLog.setDescription(getControllerMethodDescription(joinPoint).getAction() + 
                ":"+getControllerMethodDescription(joinPoint).getDescription()+
                ":"+ result.getReturnMsg());
		Object[] params = joinPoint.getArgs();
		String returnStr = "";
		if(params.length>0) {
			returnStr= JSON.toJSONString(params,SerializerFeature.IgnoreNonFieldGetter);
		}
		systemLog.setParams(returnStr);
		systemLogService.saveLog(systemLog);
		return result;
	}

	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
		// 获取session中的用户
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		request.getSession().getAttribute("user");
		Object[] params = joinPoint.getArgs();
		String returnStr = "";
		if(params.length>0) {
			returnStr= JSON.toJSONString(params, SerializerFeature.IgnoreNonFieldGetter);
		}
		// 获取请求的ip
		String ip = LoggerUtils.getCliectIp(request);
		SystemLog systemLog = SystemLog.builder()
				                       .requestip(ip)
				                       .type("2")
				                       .exceptioncode(e.getClass().getName())
				                       .exceptiondetail(e.getMessage())
				                       .actiondate(new Date())
				               		   .actionmethod(joinPoint.getSignature().getDeclaringTypeName() + "."
				            				+ joinPoint.getSignature().getName())
				                       .userid("admin")
				                       .url(request.getRequestURL().toString()).httpmethod(request.getMethod())
				                       .params(returnStr)
				                       .description(getControllerMethodDescription((ProceedingJoinPoint)joinPoint).getAction() +""
				                    		   +":"+getControllerMethodDescription((ProceedingJoinPoint)joinPoint).getDescription())
				                       .build();
		systemLogService.saveLog(systemLog);
	}
	/***
	 * 获取controller的操作信息
	 * 
	 * @param point
	 * @return
	 */
	public AnnotionDefine getControllerMethodDescription(ProceedingJoinPoint point) throws Exception {
		// 获取连接点目标类名
		String targetName = point.getTarget().getClass().getName();
		// 获取连接点签名的方法名
		String methodName = point.getSignature().getName();
		// 获取连接点参数
		Object[] args = point.getArgs();
		// 根据连接点类的名字获取指定类
		Class<?> targetClass = Class.forName(targetName);
		// 获取类里面的方法
		Method[] methods = targetClass.getMethods();
		AnnotionDefine de = new AnnotionDefine();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?>[] clazzs = method.getParameterTypes();
				if (clazzs.length == args.length) {
					de.setDescription(method.getAnnotation(LogControllerAnnotation.class).desc())
					  .setAction(method.getAnnotation(LogControllerAnnotation.class).action());
					break;
				}
			}
		}
		return de;
	}
	@Data
	@Accessors(chain=true)
	private class AnnotionDefine
	{
		private String description;
		private  ActionEnum action;
	}


}
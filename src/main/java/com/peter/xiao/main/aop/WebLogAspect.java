package com.peter.xiao.main.aop;

import com.peter.xiao.common.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/** @author Eweee */
@Aspect
@Component
public class WebLogAspect {
  private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

  /** 切入点描述这个是controller包的切入点 */
  @Pointcut("execution(public * com.peter.xiao.main.controller..*.*(..))")
  public void controllerLog() {}

  /** 切入点之前执行的事情 */
  @Before("controllerLog()")
  public void logBeforeController(JoinPoint joinPoint) {
    // 这个类是SpringMVC提供来获取请求的东西
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
    // 记录下请求内容
    logger.info("");
    logger.info(
        "---------------------------------------------------------------------------------------------------------------------------");
    logger.info("########URL:" + request.getRequestURL().toString());
    logger.info("########METHOD:" + request.getMethod());
    logger.info("########IP:" + request.getRemoteAddr());
    logger.info("########TIME:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

    logger.info("########ARGS:" + JsonUtil.toJson(request.getParameterMap()));

    // getSignature().getDeclaringTypeName()是获取包+类名的   joinPoint.getSignature.getName()获取方法名
    logger.info(
        "########CLASS_METHOD:"
            + joinPoint.getSignature().getDeclaringTypeName()
            + "."
            + joinPoint.getSignature().getName());
    // logger.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
    // logger.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    logger.info(
        "---------------------------------------------------------------------------------------------------------------------------");
    logger.info("");
  }
}

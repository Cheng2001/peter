package com.peter.xiao.interceptor;

import com.peter.xiao.annotation.Jwt;
import com.peter.xiao.common.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Eweee
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 从 http 请求头中取出 token
    String token = request.getHeader("token");
    // 如果不是映射到方法直接通过
    if(!(handler instanceof HandlerMethod)){
      return true;
    }
    HandlerMethod handlerMethod=(HandlerMethod)handler;
    Method method=handlerMethod.getMethod();
    //检查有没有需要用户权限的注解
    if (method.isAnnotationPresent(Jwt.class)) {
      Jwt jwtToken = method.getAnnotation(Jwt.class);
      if (jwtToken.required()) {
        // 执行认证
        if (token == null) {
          throw new RuntimeException("无token，请重新登录");
        }
        // 验证 token
        JwtUtil.checkSign(token);
        // 获取 token 中的 userId 存入request
        request.setAttribute("userId",JwtUtil.getUserId(token));
      }
    }
    return true;
  }
}

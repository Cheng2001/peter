package com.peter.xiao.configGuration;

import com.peter.xiao.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** @author Administrator */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
  @Autowired JwtInterceptor jwtInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 使拦截器生效
    registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
    WebMvcConfigurer.super.addInterceptors(registry);
  }
}

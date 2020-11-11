package com.peter.xiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eweee
 */
@SpringBootApplication(scanBasePackages = "com.peter.xiao")
@MapperScan("com.peter.xiao.main.mapper")
public class XiaoApplication {

  public static void main(String[] args) {
    SpringApplication.run(XiaoApplication.class, args);
  }

}

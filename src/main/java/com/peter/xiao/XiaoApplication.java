package com.peter.xiao;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Eweee
 */
@SpringBootApplication(scanBasePackages = "com.peter.xiao",exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.peter.xiao.main.mapper")
@CrossOrigin
public class XiaoApplication {

  public static void main(String[] args) {
    SpringApplication.run(XiaoApplication.class, args);
  }

}

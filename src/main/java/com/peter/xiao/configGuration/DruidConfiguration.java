package com.peter.xiao.configGuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class DruidConfiguration {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
    System.err.println("添加配置成功");
        return new DruidDataSource();
    }
}

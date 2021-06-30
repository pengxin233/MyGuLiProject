package com.px.eduCenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient //nacos服务注册
@ComponentScan("com.px")
public class ApplicationUCenter {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationUCenter.class,args);
    }
}

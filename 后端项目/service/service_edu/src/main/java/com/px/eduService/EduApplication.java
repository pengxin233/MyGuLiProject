package com.px.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@EnableDiscoveryClient //nacos注册
@EnableFeignClients//nacos服务调用
@SpringBootApplication
@ComponentScan(basePackages = {"com.px"})//设置包扫描的规则，因为他默认只扫描本启动类所在的路径，不能扫描到comment模块的内容
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}

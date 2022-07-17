package com.appleyk.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>单点登录服务端启动类</p>
 * 接口文档地址：http://localhost:8080/
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/26-16:16
 */
@SpringBootApplication
@EnableScheduling
public class SsoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SsoApplication.class);
    }
}

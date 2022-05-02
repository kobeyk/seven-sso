package com.appleyk.auth.client.configure;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>客户端模块bean自动装配配置类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:49 2022/5/2
 */
@Configuration
@EnableFeignClients(basePackages = "com.appleyk.auth.client.service.rpc")
@ComponentScan(basePackages ={"com.appleyk.auth.client"})
public class SeSsoClientAutoConfigure {
    public SeSsoClientAutoConfigure() {
        SeLoggerHelper.debug("============== seven-sso-client configured! ==============");
    }
}

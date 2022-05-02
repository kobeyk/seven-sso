package com.appleyk.auth.local.configure;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>local模块bean自动装配配置类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:54 2022/5/2
 */
@Configuration
@ComponentScan(basePackages = {"com.appleyk.auth.local"})
public class SeSsoLocalConfigure {
    public SeSsoLocalConfigure() {
        SeLoggerHelper.debug("============== seven-sso-local configured! ==============");
    }
}

package com.appleyk.auth.core.configure;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.config.SeSsoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>核心认证core模块bean自动装配配置类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-13:42
 */
@Configuration
@EnableConfigurationProperties(SeSsoProperties.class)
@ComponentScan(basePackages ={"com.appleyk.auth.core"})
@Import(ConfigSelector.class)
public class SeAuthCoreAutoConfigure {
    public SeAuthCoreAutoConfigure() {
        SeLoggerHelper.debug("============== seven-auth-core configured! ==============");
    }
}

package com.appleyk.auth.core.configure;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>自动装配</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-13:42
 */
@Configuration
@ComponentScan(basePackages ={"com.appleyk.auth.core.service"})
@MapperScan(basePackages = {"com.appleyk.auth.core.dao.mapepr"})
public class SeAuthCoreAutoConfigure {
    public SeAuthCoreAutoConfigure() {
        SeLoggerHelper.debug("============== seven-auth-core configured! ==============");
    }
}

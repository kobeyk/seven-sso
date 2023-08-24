package com.appleyk.auth.core.config;

import com.appleyk.auth.core.interceptor.SeAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>MVC配置</p>
 *
 * @author appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:46 2022/12/12
 */
@Configuration
public class SeWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public SeAuthInterceptor seAuthInterceptor(){
        return new SeAuthInterceptor();
    }

    /**添加用户令牌验证拦截器，并对所有的接口进行拦截*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(seAuthInterceptor()).addPathPatterns("/**");
    }
}

package com.appleyk.auth.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * <p>sso（单点登录）核心配置属性全部在下面了</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:41 2022/3/26
 */
@Data
@ConfigurationProperties(prefix = "se.sso")
public class SeSsoProperties {
    /**统一用户认证中心服务端点地址（sso-server服务地址）*/
    private String endpoint = "http://localhost:8080";
    /**是否用户注册时，启用验证码功能，默认启用，主要为了防止暴力注册*/
    private boolean verifyCode = true;
    /**Web站点配置文件路径*/
    private String webSitesPath = "classpath:static/websites.xml";
    /**认证用户动态表名，如果业务系统也有一个用户表的话，可以和单点的认证表结构保持一致，同时又可以扩展自己的业务*/
    private String tableName = "t_sso_user";
    @NestedConfigurationProperty
    private SeCacheProperties cache = new SeCacheProperties();
    @NestedConfigurationProperty
    private SeRedisProperties redis = new SeRedisProperties();
}
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
@ConfigurationProperties(prefix = "se.sso", ignoreUnknownFields = true)
public class SeSsoProperties {
    private String webSitesPath = "classpath:static/websites.xml";
    private String tableName = "t_sso_user";
    @NestedConfigurationProperty
    private SeCacheProperties cache = new SeCacheProperties();
    @NestedConfigurationProperty
    private SeRedisProperties redis = new SeRedisProperties();
}
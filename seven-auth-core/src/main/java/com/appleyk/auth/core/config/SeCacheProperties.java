package com.appleyk.auth.core.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>通用缓存配置</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:01 2022/4/30
 */
@Data
@NoArgsConstructor
public class SeCacheProperties {
    /** 缓存bean名称 */
    private String beanName = "local";
    /** 缓存key大小,默认缓存1w个，超过1w个，会采用缓存淘汰策略处理新的key*/
    private int size = 10000;
    /** 缓存key存活时间，默认1小时,单位毫秒*/
    private long ttl = 3600000;
    /**验证码（图形验证码，短信验证码等）超时时间，默认1分钟60s，单位秒*/
    private int codeTimeout = 60;
}

package com.appleyk.auth.core.service;

import com.appleyk.auth.core.container.SeRedisInstanceContainer;

/**
 * <p>抽象redis客户端对象操作</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:58 2022/4/2
 */
public abstract class ASeJedisPool {
    public ASeJedisPool(){
        SeRedisInstanceContainer.addJedisPool(mode(),this);
    }
    public abstract String mode();
    public abstract String setex(String key,int seconds,String value);
    public abstract String get(String key);
}

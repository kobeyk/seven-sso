package com.appleyk.auth.core.container;

import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.service.ASeJedisPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>redis客户端连接实例容器,每种redis部署模式对应一个实现bean</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:05 2022/4/2
 */
@Component
@ConditionalOnProperty(prefix = "se.sso.cache",name = "bean-name",havingValue = "redis")
public class SeRedisInstanceContainer {
    /**redis部署模式（默认单机：jedis）*/
    private static String REDIS_MODE;
    private static Map<String, ASeJedisPool> jedisPools = new HashMap<>();
    public static void addJedisPool(String redisMode, ASeJedisPool jedisPool){
        if (jedisPool == null){
            throw new RuntimeException("jedisPool is null !");
        }
        synchronized (jedisPool){
            /**判断bean是否存在*/
            if (jedisPools.containsKey(redisMode)){
                throw new RuntimeException(String.format("beanName of %s already existed！"));
            }
            jedisPools.put(redisMode,jedisPool);
        }
    }
    /**这里采用动态注入的方式来取出对应的jedisPool实例*/
    public ASeJedisPool jedisPool() throws SeException {
        if (jedisPools.size() <= 0){
            throw new SeCommonException("There are no any jedisPool beans！");
        }
        return jedisPools.get(REDIS_MODE);
    }

    @Value("${se.sso.redis.mode:jedis}")
    public void setRedisMode(String redisMode){
        REDIS_MODE = redisMode;
    }
}

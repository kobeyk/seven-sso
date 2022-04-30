package com.appleyk.auth.core.redis;

import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>redis连接池配置</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:03 2022/3/28
 */
public class SeRedisPoolConfig {

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        /**允许最大的连接*/
        config.setMaxTotal(200);
        /**允许的最大空闲连接*/
        config.setMaxIdle(50);
        config.setMinIdle(8);
        /**获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1*/
        config.setMaxWaitMillis(10000);
        /** 在获取连接的时候检查有效性, 默认false*/
        config.setTestOnBorrow(true);
        /** 调用returnObject方法时，是否进行有效检查*/
        config.setTestOnReturn(false);
        /** Idle时进行连接扫描*/
        config.setTestWhileIdle(true);
        /** 表示idle object evitor两次扫描之间要sleep的毫秒数*/
        config.setTimeBetweenEvictionRunsMillis(30000);
        /** 表示idle object evitor每次扫描的最多的对象数*/
        config.setNumTestsPerEvictionRun(10);
        /** 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义*/
        config.setMinEvictableIdleTimeMillis(60000);
        return config;
    }

}

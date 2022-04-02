package com.appleyk.auth.core.config;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.service.ASeJedisPool;
import lombok.Cleanup;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

/**
 * <p>单机版模式（matchIfMissing等于true，表示就算使用方没有配置mode属性，这里也会默认注入这个bean）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:27 2022/4/2
 */
@Component
@ConditionalOnProperty(prefix = "se.sso.redis", name = "mode", havingValue = "jedis", matchIfMissing = true)
@ConditionalOnMissingBean(value = {JedisSentinelPool.class, JedisCluster.class, ShardedJedisPool.class})
public class SeJedisPool extends ASeJedisPool implements InitializingBean, DisposableBean {

    @Autowired
    private JedisPoolConfig poolConfig;
    @Autowired
    private SeSsoProperties properties;

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        String address = properties.getRedis().getAddress();
        String[] hostAndPort = address.split(":");
        int timeout = properties.getRedis().getTimeout();
        String password = properties.getRedis().getPassword();
        jedisPool = new JedisPool(poolConfig, hostAndPort[0], Integer.valueOf(hostAndPort[1]), timeout, password, 2);
        SeLoggerHelper.info("========= Redis 单机版完成实例化!");
    }

    @Override
    public String mode() {
        return "jedis";
    }

    @Override
    public String setex(String key, int seconds, String value) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.setex(key, seconds, value);
    }

    @Override
    public String get(String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.get(key);
    }

    @Override
    public void destroy() throws Exception {
        if (jedisPool!=null){
            jedisPool.destroy();
        }
    }
}

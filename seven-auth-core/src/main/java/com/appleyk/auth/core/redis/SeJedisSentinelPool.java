package com.appleyk.auth.core.redis;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
import com.appleyk.auth.core.service.ASeJedisPool;
import lombok.Cleanup;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>哨兵模式</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:46 2022/4/2
 */
@Component
@ConditionalOnBean(SeRedisInstanceContainer.class)
@ConditionalOnProperty(prefix = "se.sso.redis", name = "mode", havingValue = "sentinel")
public class SeJedisSentinelPool extends ASeJedisPool implements InitializingBean, DisposableBean {

    @Autowired
    private JedisPoolConfig poolConfig;
    @Autowired
    private SeSsoProperties properties;

    private JedisSentinelPool jedisSentinelPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        String address = properties.getRedis().getAddress();
        String password = properties.getRedis().getPassword();
        String masterName = properties.getRedis().getMaster();
        /**xxx.xxx.xxx:26379,xxx:xxx:xxx:26379*/
        String[] addressArr = address.split(",");
        Set<String> sentinels = new HashSet<>();
        for (String addr : addressArr) {
            sentinels.add(addr);
        }
        jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig, password);
        isAvailable();
        SeLoggerHelper.debug("========= Redis Sentinel instantiation done !");
    }

    @Override
    public String mode() {
        return "sentinel";
    }

    @Override
    public String setex(String key, int seconds, String value) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.setex(key, seconds, value);
    }

    @Override
    public String get(String key) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.get(key);
    }

    @Override
    public boolean exists(String key) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.exists(key);
    }

    @Override
    public String setObject(String key, int seconds, Object value) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.setex(key.getBytes(),seconds,serialize(value));
    }

    @Override
    public Object getObject(String key) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return unSerialize(jedis.get(key.getBytes()));
    }

    @Override
    public long remove(String key) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.del(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        @Cleanup Jedis jedis = jedisSentinelPool.getResource();
        return jedis.expire(key,seconds);
    }

    @Override
    public void destroy() throws Exception {
        if (jedisSentinelPool != null) {
            jedisSentinelPool.destroy();
        }
    }
}

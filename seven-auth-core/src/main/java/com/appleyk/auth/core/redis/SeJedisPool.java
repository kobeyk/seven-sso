package com.appleyk.auth.core.redis;

import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
import com.appleyk.auth.core.service.ASeJedisPool;
import lombok.Cleanup;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@ConditionalOnBean(SeRedisInstanceContainer.class)
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
        /**消除空格*/
        String address = properties.getRedis().getAddress().replaceAll(" ","");
        if (SeGeneralUtils.isEmpty(address)){
            throw new SeCommonException("redis address is null! service start failure.");
        }
        String[] hostAndPort = address.split(":");
        String port = "6379";
        if (SeGeneralUtils.isNotEmpty(hostAndPort) && hostAndPort.length == 2){
            /**端口默认6379*/
            port = hostAndPort[1];
        }
        int timeout = properties.getRedis().getTimeout();
        String password = properties.getRedis().getPassword();
        int database = properties.getRedis().getDatabase();
        jedisPool = new JedisPool(poolConfig, hostAndPort[0], Integer.valueOf(port), timeout, password, database);
        /**完成实例化后，检查下redis服务是否可用，就是执行最简单的cmd，如果成功，则证明redis服务是ok的*/
        isAvailable();
        SeLoggerHelper.info("========= Redis local instantiation done!");
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
    public boolean exists(String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.exists(key);
    }

    @Override
    public String setObject(String key, int seconds, Object value) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.setex(key.getBytes(),seconds,serialize(value));
    }

    @Override
    public Object getObject(String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return unSerialize(jedis.get(key.getBytes()));
    }

    @Override
    public long remove(String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.del(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        return jedis.expire(key,seconds);
    }

    @Override
    public void destroy() throws Exception {
        if (jedisPool!=null){
            jedisPool.destroy();
        }
    }
}

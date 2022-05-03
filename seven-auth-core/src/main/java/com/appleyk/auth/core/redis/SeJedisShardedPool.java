package com.appleyk.auth.core.redis;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>分片模式</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:36 2022/4/2
 */
@Component
@ConditionalOnBean(SeRedisInstanceContainer.class)
@ConditionalOnProperty(prefix = "se.sso.redis", name = "mode", havingValue = "shard")
public class SeJedisShardedPool extends ASeJedisPool implements InitializingBean, DisposableBean {

    @Autowired
    private JedisPoolConfig poolConfig;
    @Autowired
    private SeSsoProperties properties;

    private ShardedJedisPool shardedJedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        String address = properties.getRedis().getAddress();
        String password = properties.getRedis().getPassword();
        List<JedisShardInfo> shardInfos = new LinkedList<>();
        String[] addressArr = address.split(",");
        for (String addr : addressArr) {
            JedisShardInfo jedisShardInfo;
            if (address.contains("redis")) {
                /**redis://xxx.xxx.xxx:6379,redis://xxx.xxx.xxx:6379*/
                jedisShardInfo = new JedisShardInfo(addr);
            } else {
                /**xxx.xxx.xxx:6379,xxx.xxx.xxx:6379*/
                String[] addressAndPort = addr.split(":");
                jedisShardInfo = new JedisShardInfo(addressAndPort[0], Integer.valueOf(addressAndPort[1]));
            }
            if (SeGeneralUtils.isNotEmpty(password)) {
                jedisShardInfo.setPassword(password);
            }
            shardInfos.add(jedisShardInfo);
        }
        shardedJedisPool = new ShardedJedisPool(poolConfig, shardInfos);
        isAvailable();
        SeLoggerHelper.debug("========= Redis shard instantiation done!");
    }

    @Override
    public String mode() {
        return "shard";
    }

    @Override
    public String setex(String key, int seconds, String value) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.setex(key, seconds, value);
    }

    @Override
    public String get(String key) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.get(key);
    }

    @Override
    public boolean exists(String key) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.exists(key);
    }

    @Override
    public String setObject(String key, int seconds, Object value) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.setex(key.getBytes(),seconds,serialize(value));
    }

    @Override
    public Object getObject(String key) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return unSerialize(jedis.get(key.getBytes()));
    }

    @Override
    public long remove(String key) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.del(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        @Cleanup ShardedJedis jedis = shardedJedisPool.getResource();
        return jedis.expire(key,seconds);
    }

    @Override
    public void destroy() throws Exception {
        if (shardedJedisPool != null) {
            shardedJedisPool.destroy();
        }
    }
}

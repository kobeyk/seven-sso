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
    @NestedConfigurationProperty
    private User user = new User();
    @NestedConfigurationProperty
    private Cache cache = new Cache();
    @NestedConfigurationProperty
    private Redis redis = new Redis();
}

@Data
class User {
    private String tableName = "t_sso_user";
}

@Data
class Cache {
    /**
     * 缓存名称
     */
    private String beanName = "local";
    /**
     * 缓存key大小,默认缓存1w个，超过1w个，会采用缓存淘汰策略处理新的key
     */
    private int size = 10000;
    /**
     * 缓存key存活时间，默认1小时,单位毫秒
     */
    private long ttl = 3600000;

}

@Data
class Redis {
    /**
     * redis地址，如：10.16.xx.xx:6379,6379端口号
     */
    private String address = "127.0.0.1:6379";
    /**
     * redis模式，共四种（哨兵属于特殊的jedis，单机其实也可以称作'副本'，只不过只有一个master而已）：
     * 单机（jedis）{@link redis.clients.jedis.Jedis} - {@link redis.clients.jedis.JedisPool}.getResource()
     * 分片（shard）{@link redis.clients.jedis.ShardedJedis} - {@link redis.clients.jedis.ShardedJedisPool}.getResource()
     * 哨兵（sentinel）{@link redis.clients.jedis.Jedis} -- {@link redis.clients.jedis.JedisSentinelPool}.getResource()
     * 集群（cluster）{@link redis.clients.jedis.JedisCluster}
     */
    private String mode = "jedis";
    /**
     * 数据库索引，默认0
     */
    private int database = 0;
    /**
     * 密码,一般用不到
     */
    private String password = null;
    /**
     * 响应超时，默认2000ms
     */
    private int timeout = 5000;
    /**
     * 哨兵模式下主节点名称
     */
    private String master = "mymaster";
}

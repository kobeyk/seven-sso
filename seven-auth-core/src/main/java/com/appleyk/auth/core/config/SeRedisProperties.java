package com.appleyk.auth.core.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>redis配置</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:03 2022/4/30
 */
@Data
@NoArgsConstructor
public class SeRedisProperties {
    /** redis地址，如：10.16.xx.xx:6379,6379端口号*/
    private String address = "127.0.0.1:6379";
    /**
     * redis模式，共四种（哨兵属于特殊的jedis，单机其实也可以称作'副本'，只不过只有一个master而已）：
     * 单机（jedis）{@link redis.clients.jedis.Jedis} - {@link redis.clients.jedis.JedisPool}.getResource()
     * 分片（shard）{@link redis.clients.jedis.ShardedJedis} - {@link redis.clients.jedis.ShardedJedisPool}.getResource()
     * 哨兵（sentinel）{@link redis.clients.jedis.Jedis} -- {@link redis.clients.jedis.JedisSentinelPool}.getResource()
     * 集群（cluster）{@link redis.clients.jedis.JedisCluster}
     */
    private String mode = "jedis";
    /**数据库索引，默认2*/
    private int database = 2;
    /**密码,一般用不到*/
    private String password = null;
    /**响应超时，默认2000ms*/
    private int timeout = 5000;
    /** 哨兵模式下主节点名称*/
    private String master = "mymaster";
}

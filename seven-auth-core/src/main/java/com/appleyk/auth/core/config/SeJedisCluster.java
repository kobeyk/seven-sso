package com.appleyk.auth.core.config;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.service.ASeJedisPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>集群模式</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:43 2022/4/2
 */
@Component
@ConditionalOnProperty(prefix = "se.sso.redis", name = "mode", havingValue = "cluster")
public class SeJedisCluster extends ASeJedisPool implements InitializingBean, DisposableBean {

    @Autowired
    private JedisPoolConfig poolConfig;
    @Autowired
    private SeSsoProperties properties;

    private JedisCluster jedisCluster;

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        String address = properties.getRedis().getAddress();
        String[] addressArr = address.split(",");
        for (String addr : addressArr) {
            String[] configs = addr.split(":");
            HostAndPort hostAndPort = new HostAndPort(configs[0], Integer.valueOf(configs[1]));
            nodes.add(hostAndPort);
        }
        jedisCluster = new JedisCluster(nodes, poolConfig);
        SeLoggerHelper.debug("========= Redis 集群版完成实例化!");
    }

    @Override
    public String mode() {
        return "cluster";
    }

    @Override
    public String setex(String key, int seconds, String value) {
        return jedisCluster.setex(key, seconds, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public void destroy() throws Exception {
        if (jedisCluster != null) {
            jedisCluster.close();
        }
    }
}

package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
import com.appleyk.auth.core.dict.SeCacheBeanNameConsts;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ASeJedisPool;
import com.appleyk.auth.core.service.ASeSessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * <p>redis实现服务端用户session缓存</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:24 2022/3/26
 */
@Service
@ConditionalOnBean(SeRedisInstanceContainer.class)
public class SeRedisCacheImpl extends ASeSessionCache {

    @Autowired
    private SeRedisInstanceContainer redisContainer;

    @Autowired
    private SeSsoProperties ssoProperties;

    @Override
    public String cacheName() {
        return SeCacheBeanNameConsts.REDIS;
    }

    @Override
    public void put(Long userId, SeSsoInfo ssoInfo) throws SeException {
        int timeout = ssoProperties.getRedis().getTimeout();
        redisContainer.jedisPool().setObject(userId.toString(),timeout,ssoInfo);
    }

    @Override
    public void remove(Long userId) throws SeException {
        redisContainer.jedisPool().remove(userId.toString());
    }

    @Override
    public SeSsoInfo get(Long userId) {
        SeSsoInfo ssoInfo = null;
        try{
            ASeJedisPool jedisPool = redisContainer.jedisPool();
            int timeout = ssoProperties.getRedis().getTimeout();
            if (jedisPool.exists(userId.toString())){
                /**续期*/
                jedisPool.expire(userId.toString(),timeout);
                ssoInfo = (SeSsoInfo) redisContainer.jedisPool().getObject(userId.toString());
            }
        }catch (Exception e){
            SeLoggerHelper.error(e.getMessage(),e);
            return null;
        }
        return ssoInfo;
    }

    @Override
    public void put(String key, String code) throws SeException {
         int codeTimeout = ssoProperties.getCache().getCodeTimeout();
         redisContainer.jedisPool().setex(key,codeTimeout,code);
    }

    @Override
    public void remove(String key) throws SeException {
        redisContainer.jedisPool().remove(key);
    }

    @Override
    public String get(String key) {
        String result;
        try{
            result = redisContainer.jedisPool().get(key);
        }catch (Exception e){
            SeLoggerHelper.error(e.getMessage(),e);
            return null;
        }
        return result;
    }

}

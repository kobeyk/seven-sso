package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.dict.SeCacheBeanNameConsts;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ASeSessionCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * <p>redis实现用户session缓存</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:24 2022/3/26
 */
@Service
@ConditionalOnProperty(prefix = "se.sso.cache",name = "beanName",havingValue = "redis")
public class SeRedisCacheImpl extends ASeSessionCache {

    @Override
    public String cacheName() {
        return SeCacheBeanNameConsts.REDIS;
    }

    @Override
    public void put(Long userId, SeSsoInfo ssoInfo) throws SeException {

    }

    @Override
    public void remove(Long userId) throws SeException {

    }

    @Override
    public SeSsoInfo get(Long userId) {
        return null;
    }

    @Override
    public void put(String key, String code) throws SeException {

    }

    @Override
    public void remove(String key) throws SeException {

    }

    @Override
    public String get(String key) {
        return null;
    }
}

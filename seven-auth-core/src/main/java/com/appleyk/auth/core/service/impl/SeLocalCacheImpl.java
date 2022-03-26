package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.core.dict.SeCacheBeanNameConsts;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ASeSessionCache;
import org.springframework.stereotype.Service;

/**
 * <p>越努力，越幸运</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:26 2022/3/26
 */
@Service
public class SeLocalCacheImpl extends ASeSessionCache {

    @Override
    public String cacheName() {
        return SeCacheBeanNameConsts.LOCAL;
    }

    @Override
    public void put(Long userId, SeSsoInfo ssoInfo) {

    }

    @Override
    public void remove(Long userId) {

    }

    @Override
    public SeSsoInfo get(Long userId) {
        return null;
    }

    @Override
    public void put(String key, String code) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public String get(String key) {
        return null;
    }
}

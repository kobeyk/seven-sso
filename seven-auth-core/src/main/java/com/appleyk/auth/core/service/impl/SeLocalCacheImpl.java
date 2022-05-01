package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.dict.SeCacheBeanNameConsts;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ASeSessionCache;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>expiringMap（基于内存的一个Map）实现服务端用户session缓存</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午11:26 2022/3/26
 */
@Service
@ConditionalOnProperty(prefix = "se.sso.cache",name = "bean-name",havingValue = "local")
public class SeLocalCacheImpl extends ASeSessionCache implements InitializingBean {

    @Autowired
    private SeSsoProperties ssoProperties;

    private ExpiringMap<Long,SeSsoInfo> ssoInfoMap;

    private ExpiringMap<String, String> codeMap;

    @Override
    public void afterPropertiesSet(){
        long ttl = ssoProperties.getCache().getTtl();
        int maxSize = ssoProperties.getCache().getSize();
        long codeTimeout = ssoProperties.getCache().getCodeTimeout();
        this.ssoInfoMap = ExpiringMap.builder()
                .maxSize(maxSize)
                .expiration(ttl, TimeUnit.MILLISECONDS)
                /**每次访问缓存时，过期倒计时清零*/
                .expirationPolicy(ExpirationPolicy.ACCESSED)
                .variableExpiration().build();
        this.codeMap = ExpiringMap.builder()
                .maxSize(maxSize)
                .expiration(codeTimeout,TimeUnit.SECONDS)
                 /**每次更新缓存时，过期倒计时清零*/
                .expirationPolicy(ExpirationPolicy.CREATED)
                .variableExpiration().build();
        SeLoggerHelper.info("========== memory-based cache init ===========");
    }

    @Override
    public String cacheName() {
        return SeCacheBeanNameConsts.LOCAL;
    }

    @Override
    public void put(Long userId, SeSsoInfo ssoInfo) throws SeException {
        ssoInfoMap.put(userId,ssoInfo);
    }

    @Override
    public void remove(Long userId) throws SeException {
        ssoInfoMap.remove(userId);
    }

    @Override
    public SeSsoInfo get(Long userId) {
        /**这里每次访问，都会触发过期倒计时清零*/
        return ssoInfoMap.get(userId);
    }

    @Override
    public void put(String key, String code) throws SeException {
        codeMap.put(key,code);
    }

    @Override
    public void remove(String key) throws SeException {
        codeMap.remove(key);
    }

    @Override
    public String get(String key) {
        return codeMap.get(key);
    }

}

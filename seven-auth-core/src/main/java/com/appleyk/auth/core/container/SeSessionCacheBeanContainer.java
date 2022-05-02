package com.appleyk.auth.core.container;

import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.service.ASeSessionCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>服务端用户认证信息缓存bean容器</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:55 2022/3/26
 */
@Component
public class SeSessionCacheBeanContainer {
    /**缓存模式（默认：local）*/
    private static String CACHE_BEAN_NAME;
    private static Map<String, ASeSessionCache> caches = new HashMap<>();
    public static void addCacheBean(String cacheBeanName,ASeSessionCache sessionCache){
        if (sessionCache == null){
            throw new RuntimeException("sessionCache is null !");
        }
        synchronized (caches){
            /**判断bean是否存在*/
            if (caches.containsKey(cacheBeanName)){
                throw new RuntimeException(String.format("beanName of %s already existed！"));
            }
            caches.put(cacheBeanName,sessionCache);
        }
    }
    /**这里采用动态注入的方式来取出对应的cache实例*/
    public static ASeSessionCache getSessionCache() throws SeException {
        if (caches.size() <= 0){
            throw new SeCommonException("There are no cache beans for user session！");
        }
        return caches.get(CACHE_BEAN_NAME);
    }

    @Value("${se.sso.cache.bean-name:local}")
    public void setCacheBeanName(String cacheBeanName){
        CACHE_BEAN_NAME = cacheBeanName;
    }
}

package com.appleyk.auth.core.service;

import com.appleyk.auth.core.container.SeSessionCacheBeanContainer;
import com.appleyk.auth.core.model.session.SeSsoInfo;

/**
 * <p>抽象会话缓存类，实现默认的checkToken，剩下的交给子类去实现</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:59 2022/3/26
 */
public abstract class ASeSessionCache implements ISeSessionCache{

    public ASeSessionCache(){
        SeSessionCacheBeanContainer.addCacheBean(this.cacheName(),this);
    }

    public abstract String cacheName();

    @Override
    public SeSsoInfo checkToken(String token) {
        return null;
    }
}

package com.appleyk.auth.core.service;

import com.appleyk.auth.core.container.SeAuthListenerBeanContainer;
import com.appleyk.auth.core.model.SeAuthUser;

/**
 * <p>抽象用户认证监听器（主要定义回调方法，让子应用系统选择性的去重写）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-15:41
 */
public abstract class ASeAuthListener {

    public ASeAuthListener(){
        SeAuthListenerBeanContainer.addListener(this.order(),this);
    }

    /**子类必须重写，监听器名称必须全局唯一*/
    public abstract String listenerName();

    /**监听器加载（调用）顺序，默认0的话，优先级最高*/
    public int order(){
        return 0;
    }

    /**
     * 用户登录后回调
     * @param authUser 认证用户
     */
    public void afterLogin(SeAuthUser authUser){

    }

    /**
     * 用户退出登录前回调
     * @param authUser 认证用户
     */
    public void beforeLogout(SeAuthUser authUser){

    }
}

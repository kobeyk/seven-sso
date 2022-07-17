package com.appleyk.auth.core.service;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.model.session.SeSsoInfo;

/**
 * <p>用户会话缓存接口（实现方式有多种，内存 or redis）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-15:13
 */
public interface ISeSessionCache {

    /***
     * 缓存用户session
     * @param userId   用户id，作为缓存key
     * @param ssoInfo  用户信息（包括登录和认证的）
     */
    void put(Long userId, SeSsoInfo ssoInfo) throws SeException;

    /***
     * 清除指定用户的会话信息，主要用于用户退出登录时
     * @param userId 用户id
     */
    void remove(Long userId) throws SeException;

    /** 获取指定用户的session缓存信息*/
    SeSsoInfo get(Long userId);

    /**
     * 获取指定key的缓存数据
     */
    String get(String key);

    /**
     * 检查用户令牌是否有效
     * @param token 用户令牌
     * @return 用户信息
     */
    SeSsoInfo checkToken(String token) throws SeException;

    /***
     * 缓存验证码值
     * @param key 可以是username（因为其唯一），也可以是手机号（因为其也唯一）
     * @param code 可以是短信验证码，也可以是图片验证码，当然其他的也支持
     */
    void put(String key,String code) throws SeException;

    /** 清除指定key的缓存数据，如清除手机号或用户名对应的code值*/
    void remove(String key) throws SeException;

}

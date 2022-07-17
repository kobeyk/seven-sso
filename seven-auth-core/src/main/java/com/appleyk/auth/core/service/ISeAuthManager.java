package com.appleyk.auth.core.service;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;

import java.util.Map;

/**
 * <p>用户认证管理接口</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-16:27
 */
public interface ISeAuthManager {

    /**
     * 注册用户
     * @param userName 用户名称
     * @param password 用户密码
     * @return 认证用户
     * @throws SeException
     */
    SeAuthUser register(String userName,String password) throws SeException;

    /**
     * 注册用户附带详情
     * @param userName 用户名称
     * @param password 用户密码
     * @param info 用户信息
     * @return 认证用户
     * @throws SeException
     */
    SeAuthUser register(String userName, String password, Map<String,Object> info) throws SeException;

    /**
     * 按用户名、用户密码和应用ID进行登录
     * @param userName 用户名称
     * @param password 用户密码
     * @param appId 应用系统ID
     * @return 服务端用户认证缓存信息
     */
    SeSsoInfo login(String userName,String password,Long appId) throws SeException;

    /**
     * 退出登录
     * @param token 用户令牌
     */
    void logout(String token) throws SeException;

    /**
     * 按用户令牌进行登录（利于业务系统缓存该token，以免每次checkToken都要走sso-server）
     * @param token 用户令牌
     * @return 服务端认证用户session
     */
    SeSsoInfo loginToken(String token) throws SeException;

    /***
     * 校验用户令牌是否合法
     * @param token 用户令牌
     * @return 服务端认证用户session
     */
    SeSsoInfo checkToken(String token) throws SeException;

    /**
     * 验证key对应的验证码code值是否合法
     * @param key 可以是userName,也可以是phone，获其他
     * @param code 验证码，可以使图片验证码、短信验证码或其他
     */
    void verifyCode(String key,String code) throws SeException;

    /***
     * 第三方登录实现，1.0版本暂不支持
     */
}

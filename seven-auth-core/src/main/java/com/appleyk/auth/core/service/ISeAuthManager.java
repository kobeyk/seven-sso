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
    SeSsoInfo login(String userName,String password,Long appId);

    /**
     * 按用户令牌进行登录
     * @param token 用户令牌
     * @param appId 应用系统ID
     * @return 服务端用户认证缓存信息
     */
    SeSsoInfo loginToken(String token,Long appId);

    /***
     * 校验用户令牌是否合法
     * @param token 用户令牌
     * @return 服务端用户认证缓存信息
     */
    SeSsoInfo checkToken(String token);

    /**
     * 退出登录
     * @param token 用户令牌
     */
    void logout(String token);

    /***
     * 第三方登录实现，1.0版本暂不支持
     */
}

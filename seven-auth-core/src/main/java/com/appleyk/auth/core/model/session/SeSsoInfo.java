package com.appleyk.auth.core.model.session;

import com.appleyk.auth.core.model.SeAuthUser;

/**
 * <p>
 *     用户登录信息（包含用户基本信息+登录信息，主要用于缓存）
 * </p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-9:40
 */
public class SeSsoInfo {
    /**认证用户*/
    private SeAuthUser authUser;
    /**登录应用ID*/
    private Long appId;
    /** 当 AppId=0时，即单应用系统时，使用该token*/
    private String localToken;
    /** 当 AppId<>0时，即多应用系统时，使用该token*/
    private String clientToken;
    /** 回调地址，当登录带上AppId且登录成功时，会返回该值，即前端拿到该地址后即可跳转到相应的应用首页*/
    private String callbackUrl;
}

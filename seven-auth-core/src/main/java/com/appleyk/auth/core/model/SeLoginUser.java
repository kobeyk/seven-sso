package com.appleyk.auth.core.model;

import lombok.Data;

/**
 * <p>登录用户</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-14:04
 */
@Data
public class SeLoginUser extends SeAuthUser{
    /**应用标识（每一个应用系统对应一个id，一个id对应一个回调地址）*/
    private Long appId;
}

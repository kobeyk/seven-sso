package com.appleyk.auth.core.model;

import com.appleyk.auth.core.dict.ESeCodeType;

/**
 * <p>注册用户</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-14:13
 */
public class SeRegisteredUser extends SeAuthUser{
    /**确认密码*/
    private String rePassword;
    /** 验证码验证方式，1：短信验证码，2：图片验证码*/
    private ESeCodeType codeType;
    /**验证码，可以是图形验证码、短信验证码或其他（支持扩展）等*/
    private String code;
}

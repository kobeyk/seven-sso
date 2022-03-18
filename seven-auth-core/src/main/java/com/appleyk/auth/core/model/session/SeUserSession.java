package com.appleyk.auth.core.model.session;

/**
 * <p>服务端用户session信息</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-16:30
 */
public class SeUserSession {
    private long id;
    private String name;
    /** 单机版（即App应用系统只有一个，与单点登录服务是捆绑在一起的）令牌*/
    private String token;
    /** */
    private String clientToken;
}

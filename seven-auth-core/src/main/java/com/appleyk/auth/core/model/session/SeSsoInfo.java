package com.appleyk.auth.core.model.session;

import com.appleyk.auth.core.model.SeAuthUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
@Data
public class SeSsoInfo {
    /**认证用户*/
    private SeAuthUser authUser;
    /**登录应用ID*/
    private Long appId;
    /** 当 AppId=0 或 不填写时，即单应用系统站点时，使用该token*/
    private String localToken;
    /** 当 AppId<>0时，即多应用系统站点时，使用该token*/
    private String clientToken;
    /** 回调地址，当登录带上AppId且登录成功时，会返回该值，即前端拿到该地址后即可跳转到相应的应用首页*/
    private String callbackUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAccessTime;
    public long fetchUserId(){
        if (getAuthUser() == null){
            return 0L;
        }
        return getAuthUser().getId();
    }
    public String fetchUserName(){
        if (getAuthUser() == null){
            return "";
        }
        return getAuthUser().getName();
    }
}

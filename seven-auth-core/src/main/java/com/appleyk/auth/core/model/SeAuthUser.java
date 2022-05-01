package com.appleyk.auth.core.model;

import com.appleyk.auth.core.model.base.SeCheckStatus;
import com.appleyk.auth.core.model.base.SeObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * <p>认证用户</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-13:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeAuthUser extends SeObject {
    /**别名*/
    private String alias;
    /**头像*/
    private String avatar;
    /**密码*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    /**用户其他信息*/
    private Map<String,Object> info;
    /**用户创建时间（注册时间）*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("cTime")
    private Date cTime;
    /**用户信息修改时间（如改密、改昵称、改头像...etc）*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty("uTime")
    private Date uTime;
    public SeAuthUser(Long id,String name,String password){
        super(id,name, SeCheckStatus.PASSED);
        this.password = password;
    }
    public SeAuthUser(Long id,String name,String password,Map<String,Object> info){
        super(id,name, SeCheckStatus.PASSED);
        this.password = password;
        this.info = info;
    }
}

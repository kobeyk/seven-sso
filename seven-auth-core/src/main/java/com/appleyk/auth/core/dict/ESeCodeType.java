package com.appleyk.auth.core.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>验证码类型</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-14:19
 */
public enum ESeCodeType {

    SMS(1,"短信验证码"),
    IMAGE(2,"图片验证码");

    private final Integer code;
    private final String name;

    ESeCodeType(Integer code, String name){
        this.name = name;
        this.code = code;
    }

    @JsonCreator
    public static ESeCodeType getType(int code){
        for(ESeCodeType type : ESeCodeType.values()){
            if(type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

package com.appleyk.auth.common.excep;

import com.appleyk.auth.common.core.ESeResponseCode;

/**
 * <p>通用异常，为什么说是通用呢，因为支持传码值，通过码值，大部分异常都可以描述出来</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/17-15:19
 */
public class SeCommonException extends SeException{
    public SeCommonException(String message){
        super(ESeResponseCode.FAIL.getCode(), message);
    }

    /**
     * @param code 外部定义异常码
     * @param message 外部定义异常消息
     */
    public SeCommonException(ESeResponseCode code, String message){
        super(code.getCode(),message);
    }
}

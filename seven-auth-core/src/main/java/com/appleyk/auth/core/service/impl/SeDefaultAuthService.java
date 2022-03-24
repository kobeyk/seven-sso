package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.service.ISeAuthManager;

import java.util.Map;

/**
 * <p>认证管理默认实现</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-16:47
 */
public abstract class SeDefaultAuthService implements ISeAuthManager {

    @Override
    public SeAuthUser register(String userName, String password) throws SeException {
        return null;
    }

    @Override
    public SeAuthUser register(String userName, String password, Map<String, Object> info) throws SeException {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    public void doAfterLogin(){

    }
}

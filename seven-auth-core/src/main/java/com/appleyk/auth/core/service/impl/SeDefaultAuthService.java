package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.dao.mapper.SeUserMapper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.service.ISeAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>认证管理默认实现,只实现一些功能不变固定的业务逻辑，如用户注册</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-16:47
 */
@Service
public abstract class SeDefaultAuthService implements ISeAuthManager {

    @Autowired
    private SeUserMapper userMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SeAuthUser register(String userName, String password) throws SeException {
        /**保证用户名全局唯一，先查*/
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

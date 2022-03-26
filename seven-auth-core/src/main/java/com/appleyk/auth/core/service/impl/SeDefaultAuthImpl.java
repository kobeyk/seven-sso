package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.ids.SeIdProducer;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ISeAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public abstract class SeDefaultAuthImpl implements ISeAuthManager {

    @Autowired
    private SeAuthUserImpl userService;

    @Override
    public SeAuthUser register(String userName, String password) throws SeException {
        SeAuthUser authUser = new SeAuthUser(SeIdProducer.getID(),userName,password);
        userService.insert(authUser);
        return authUser;
    }

    @Override
    public SeAuthUser register(String userName, String password, Map<String, Object> info) throws SeException {
        SeAuthUser authUser = new SeAuthUser(SeIdProducer.getID(),userName,password,info);
        userService.insert(authUser);
        return authUser;
    }

    @Override
    public SeSsoInfo login(String userName, String password, Long appId) {

        return null;
    }

    @Override
    public void logout(String token) {

    }

}

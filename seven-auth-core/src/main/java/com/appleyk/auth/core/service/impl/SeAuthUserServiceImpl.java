package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.core.SePage;
import com.appleyk.auth.common.core.filter.SeFilter;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.configure.SeDynamicTableConfig;
import com.appleyk.auth.core.dao.entity.SeUserEntity;
import com.appleyk.auth.core.dao.mapper.SeUserMapper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.service.ISeAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p></p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/25-15:20
 */
@Service
public class SeAuthUserServiceImpl implements ISeAuthUser {

    @Autowired
    private SeUserMapper userMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean resetUserName(String oldName, String newName) throws SeException {
        SeUserEntity entity = userMapper.findUserByName(oldName, SeDynamicTableConfig.tableName);
        if (SeGeneralUtils.isEmpty(entity)){

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean resetPassword(String userName, String oldPwd, String newPwd) throws SeException {
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SeAuthUser update(SeAuthUser authUser) throws SeException {
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean disabledUser(Long uid) {
        return false;
    }

    @Override
    public SeUserEntity findByName(String userName) {
        return null;
    }

    @Override
    public SeUserEntity findById(Long uid) {
        return null;
    }

    @Override
    public SePage<SeAuthUser> findAll(SeFilter filter) {
        return null;
    }
}

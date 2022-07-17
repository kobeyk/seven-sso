package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.core.SePage;
import com.appleyk.auth.common.core.filter.SeFilter;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeFilterHelper;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.helper.SePageHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.common.util.SeJsonUtils;
import com.appleyk.auth.common.util.SeMD5Encrypt;
import com.appleyk.auth.core.config.SeDynamicTableConfig;
import com.appleyk.auth.core.dao.entity.SeUserEntity;
import com.appleyk.auth.core.dao.mapper.SeUserMapper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.service.ISeAuthUser;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>用户认证业务实现层</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/25-15:20
 */
@Service
public class SeAuthUserImpl implements ISeAuthUser {

    @Autowired
    private SeUserMapper userMapper;

    public SeAuthUserImpl() {
        System.out.println();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SeAuthUser save(SeAuthUser authUser) throws SeException {
        if (SeGeneralUtils.isEmpty(authUser.getId())) {
            throw new SeCommonException(ESeResponseCode.OBJECT_ID_NOT_EXIST, "用户ID不允许空！");
        }
        SeAuthUser userDb = findByName(authUser.getName());
        if (SeGeneralUtils.isNotEmpty(userDb)){
            throw new SeCommonException(ESeResponseCode.OBJECT_IS_EXIST,"用户名已被占用！");
        }
        authUser.setCTime(new Date());
        authUser.setUTime(authUser.getCTime());
        if (userMapper.insert(SeUserEntity.createEntity(authUser)) >0){
            return authUser;
        }else{
            throw new SeCommonException(ESeResponseCode.DATA_CREATE_ERROR,"新增用户失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SeAuthUser update(SeAuthUser authUser) throws SeException {
        if (SeGeneralUtils.isEmpty(authUser.getId())) {
            throw new SeCommonException(ESeResponseCode.OBJECT_ID_NOT_EXIST, "用户ID不允许空！");
        }
        SeAuthUser oldUser = findById(authUser.getId());
        if (SeGeneralUtils.isEmpty(oldUser)){
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST, "当前用户不存在！");
        }
        SeUserEntity userEntity = new SeUserEntity();
        userEntity.setUid(authUser.getId());
        /**只能更新昵称、头像和基本信息*/
        userEntity.setAlias(authUser.getAlias());
        userEntity.setAvatar(authUser.getAvatar());
        if (SeGeneralUtils.isNotEmpty(authUser.getInfo())) {
            userEntity.setInfo(SeJsonUtils.object2Json(authUser.getInfo()));
        }
        userEntity.setUTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateUserByUserName(userEntity, SeDynamicTableConfig.tableName);
        oldUser.setAlias(authUser.getAlias());
        oldUser.setAvatar(authUser.getAvatar());
        oldUser.setUTime(new Date(userEntity.getUTime().getTime()));
        oldUser.setInfo(authUser.getInfo());
        oldUser.setPassword(null);
        return oldUser;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean resetUserName(String oldName, String newName) throws SeException {
        String tableName = SeDynamicTableConfig.tableName;
        checkUserName(oldName);
        SeUserEntity newUserEntity = userMapper.findUserByName(newName, tableName);
        if (SeGeneralUtils.isNotEmpty(newUserEntity)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_IS_EXIST, "新用户名已存在！");
        }
        return userMapper.updateUserName(oldName, newName, tableName) > 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean resetPassword(String userName, String oldPwd, String newPwd) throws SeException {
        String tableName = SeDynamicTableConfig.tableName;
        SeUserEntity userEntity = checkUserName(userName);
        String oldPwdInDb = userEntity.getPassword();
        try {
            if (!SeMD5Encrypt.valid(oldPwd, oldPwdInDb)) {
                throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "原密码不对！");
            }
            userEntity.setPassword(SeMD5Encrypt.encrypt(newPwd));
            return userMapper.updateUserByUserName(userEntity, tableName) > 0;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            SeLoggerHelper.error(e.getMessage());
            return false;
        }
    }

    private SeUserEntity checkUserName(String userName) throws SeException {
        SeUserEntity userEntity = userMapper.findUserByName(userName, SeDynamicTableConfig.tableName);
        if (SeGeneralUtils.isEmpty(userEntity)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST, "用户名不存在！");
        }
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean userCheck(Long uid, int status) throws SeException {
        if (SeGeneralUtils.isEmpty(uid)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_ID_NOT_EXIST, "用户ID不允许空！");
        }
        SeUserEntity userEntity = userMapper.findUserByUid(uid, SeDynamicTableConfig.tableName);
        if (SeGeneralUtils.isEmpty(userEntity)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST, "未查询到指定用户！");
        }
        userEntity.setStatus(status);
        userEntity.setUTime(new Timestamp(System.currentTimeMillis()));
        return userMapper.updateUserByUserName(userEntity, SeDynamicTableConfig.tableName) > 0;
    }

    @Override
    public SeAuthUser findByName(String userName) throws SeException {
        SeUserEntity userEntity = userMapper.findUserByName(userName, SeDynamicTableConfig.tableName);
        return buildAuthUser(userEntity);
    }

    @Override
    public SeAuthUser findById(Long uid) throws SeException {
        SeUserEntity userEntity = userMapper.findUserByUid(uid, SeDynamicTableConfig.tableName);
        return buildAuthUser(userEntity);
    }

    private SeAuthUser buildAuthUser(SeUserEntity userEntity) throws SeException {
        if (SeGeneralUtils.isEmpty(userEntity)) {
           return null;
        }
        SeAuthUser user = SeUserEntity.createModel(userEntity);
        if (user == null) {
            throw new SeCommonException("数据实体对象转业务模型对象失败！");
        }
        return user;
    }

    @Override
    public SePage<SeAuthUser> findAll(SeFilter filter) {
        /**分页拦截器参数配置*/
        SeFilterHelper.pageIntercept(filter);
        Page<SeUserEntity> userEntitiesPage = (Page<SeUserEntity>) userMapper.findAll(SeDynamicTableConfig.tableName);
        if (SeGeneralUtils.isEmpty(userEntitiesPage) || SeGeneralUtils.isEmpty(userEntitiesPage.getResult())) {
            return new SePage<>();
        }
        List<SeAuthUser> authUsers = new ArrayList<>();
        userEntitiesPage.forEach(u -> {
            try {
                SeAuthUser user = buildAuthUser(u);
                if (user!=null){
                    authUsers.add(user);
                }
            } catch (Exception e) {
            }
        });
        return SePageHelper.buildPage(authUsers, userEntitiesPage);
    }
}

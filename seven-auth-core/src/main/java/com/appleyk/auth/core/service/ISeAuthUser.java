package com.appleyk.auth.core.service;

import com.appleyk.auth.common.core.SePage;
import com.appleyk.auth.common.core.filter.SeFilter;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.dao.entity.SeUserEntity;
import com.appleyk.auth.core.model.SeAuthUser;

/**
 * <p>认证用户接口定义，定义一些用户增删改查相关的方法</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/25-15:04
 */
public interface ISeAuthUser {

    /**
     * 重置用户名（功能保留在service层，controller层面不提供接口，毕竟用户名不能轻易修改）
     * @param oldName 旧用户名
     * @param newName 新用户名
     * @return true or false
     * @exception SeException 重置失败，因为用户名已存在！
     */
    default boolean resetUserName(String oldName,String newName) throws SeException {
        return false;
    };

    /**
     * 重置用户密码
     * @param userName 用户名
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return true or false
     * @exception SeException 重置失败，因为老密码可能不对！
     */
    boolean resetPassword(String userName,String oldPwd,String newPwd) throws SeException;

    /**
     * 更新用户信息，如更新昵称、头像、info信息等...
     * @param authUser 业务对象
     * @return 更新后的业务对象
     * @exception SeException 更新失败，可能传入的模型对应的name在库中没有！
     */
    SeAuthUser update(SeAuthUser authUser) throws SeException;

    /**
     * 禁用用户，即将用户的状态改为:SeCheckStatus.NO_PASS,禁用状态的用户无法进行认证！
     * @param uid 用户id
     * @return true or false
     */
    boolean disabledUser(Long uid);

    SeUserEntity findByName(String userName);
    SeUserEntity findById(Long uid);
    SePage<SeAuthUser> findAll(SeFilter filter);
}

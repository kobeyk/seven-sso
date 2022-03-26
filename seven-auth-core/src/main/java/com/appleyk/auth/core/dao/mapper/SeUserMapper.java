package com.appleyk.auth.core.dao.mapper;

import com.appleyk.auth.core.dao.entity.SeUserEntity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 *     用户mapper接口
 *     如果看过mybatis源码，你就知道一个mapper就是一个CRUD的代理合集
 *     接口中定义的每一个方法都对应一条sql语句
 * </p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/24-14:16
 */
public interface SeUserMapper extends Mapper<SeUserEntity> {

    /**
     * 根据用户名查找用户
     * @param username 用户名称
     * @param tableName 动态表名（注入）
     */
    SeUserEntity findUserByName(@Param("username") String username, @Param("tableName") String tableName);

    /**
     * 更新用户信息
     */
    Integer updateUserByUserName(@Param("userEntity") SeUserEntity userEntity, @Param("tableName") String tableName);

    /**
     * 通过id查找用户
     */
    SeUserEntity findUserByUid(@Param("id") Long uId, @Param("tableName") String tableName);

    /**
     * 更新用户名（一般系统更改用户名是有限制次数的，比如一个月只能改一次）
     * @param oldName 旧用户名
     * @param newName 新用户名
     * @param tableName 表名
     */
    Integer updateUserName(@Param("oldName") String oldName,@Param("newName") String newName,@Param("tableName") String tableName);

    /**
     * 统计注册用户数量
     */
    Long countUsers(@Param("tableName") String tableName);

    /**
     * 查询所有用户
     */
    List<SeUserEntity> findAll(@Param("tableName") String tableName);

}

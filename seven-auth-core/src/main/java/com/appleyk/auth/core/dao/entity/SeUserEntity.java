package com.appleyk.auth.core.dao.entity;

import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.common.util.SeJsonUtils;
import com.appleyk.auth.common.util.SeMD5Encrypt;
import com.appleyk.auth.core.config.SeDynamicTableConfig;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.base.SeCheckStatus;
import lombok.Data;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>用户数据集实体类（面向db层）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/24-13:09
 */
@Data
public class SeUserEntity implements IDynamicTableName {

    @Id
    private Long uid;
    private String name;
    private String password;
    private String alias;
    private String avatar;
    @Column(name = "ctime")
    private Timestamp cTime;
    @Column(name = "utime")
    private Timestamp uTime;
    /**一定要用包装类型，否则mybatis mapper插入数据库时会忽略*/
    private Integer status = SeCheckStatus.PASSED;
    private String info;

    public SeUserEntity() {

    }

    private SeUserEntity(SeAuthUser authUser) throws Exception {
        this.uid = authUser.getId();
        this.name = authUser.getName();
        this.password = SeMD5Encrypt.encrypt(authUser.getPassword());
        this.alias = SeGeneralUtils.isEmpty(authUser.getAlias()) ? this.name : authUser.getAlias();
        this.avatar = authUser.getAvatar();
        this.info = authUser.getInfo() == null ? "{}" : SeJsonUtils.object2Json(authUser.getInfo());
        this.cTime = authUser.getCTime() == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(authUser.getCTime().getTime());
        this.uTime = authUser.getUTime() == null ? this.cTime : new Timestamp(authUser.getUTime().getTime());
    }

    public static SeUserEntity createEntity(SeAuthUser authUser) {
        try {
            return new SeUserEntity(authUser);
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage());
            return null;
        }
    }

    public static SeAuthUser createModel(SeUserEntity entity) {
        if (entity == null) {
            return null;
        }
        try{
            SeAuthUser authUser = SeAuthUser.builder().alias(entity.alias)
                    .info(SeGeneralUtils.isEmpty(entity.getInfo()) ? null : SeJsonUtils.json2Map(entity.getInfo()))
                    .avatar(entity.getAvatar())
                    .cTime(new Date(entity.getCTime().getTime())).build();
            authUser.setId(entity.getUid());
            authUser.setName(entity.getName());
            authUser.setPassword(entity.getPassword());
            authUser.setUTime(entity.getUTime());
            authUser.setCheckStatus(entity.getStatus());
            return authUser;
        }catch (Exception e){
            SeLoggerHelper.error(e.getMessage());
            return null;
        }
    }

    /** 实现动态表名 */
    @Override
    public String getDynamicTableName() {
        return SeDynamicTableConfig.tableName;
    }
}

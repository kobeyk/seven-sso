package com.appleyk.auth.core.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 *    认证用户表名动态注入（单点服务端可能叫t_sso_user,
 *    但是业务系统引用local模块，可能任意起名，所以这里不要限制死）
 * </p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/25-15:44
 */
public class SeDynamicTableConfig {

    public static String tableName;

    public String getTableName() {
        return tableName;
    }

    @Value("${se.sso.table-name:t_sso_user}")
    public void setTableName(String tableName) {
        SeDynamicTableConfig.tableName = tableName;
    }
}

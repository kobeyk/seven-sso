package com.appleyk.auth.common.core;

/**
 * <p>Http响应码（正常 & 异常）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/17-15:23
 */
public enum ESeResponseCode {
    OK(200, "成功"),
    FAIL(400, "失败"),
    FIELD_MISSING(10000, "字段缺失"),
    FILED_VALUE_INVALID(10001, "字段值无效/不合法"),
    OBJECT_NOT_EXIST(10101, "对象不存在"),
    OBJECT_IS_EXIST(10102, "对象已存在"),
    OBJECT_NAME_NULL(10103, "对象名称空"),
    OBJECT_ID_NULL(10104, "对象ID空"),
    OBJECT_NAME_REPEATED(10105, "对象名称重复"),
    OBJECT_ID_REPEATED(10106, "对象ID重复"),
    OBJECT_ID_NOT_EXIST(10107, "对象ID不存在"),
    OBJECT_ID_NOT_NULL(10108, "对象的ID不允许空"),
    OBJECT_NAME_NOT_NULL(10109, "对象名称不允许空"),
    INVALID_REQUEST(10200, "非法的请求"),
    INVALID_CLIENT(10201, "用户认证失败"),
    INVALID_GRANT(10202, "非法的授权信息"),
    EXPIRED_TOKEN(10203, "令牌过期"),
    ACCESS_DENIED(10204, "用户或授权服务器拒绝授予数据访问权限"),
    INVALID_USER_ID(10205, "无效的用户ID"),
    INADEQUATE_PERMISSIONS(10206, "用户权限不足，禁止访问!"),
    INVALID_APP_ID(10207, "无效的应用站点ID!"),
    WEEK_PASSWORD(10208, "无效的应用站点ID!"),
    INVALID_PASSWORD(10209, "无效的应用站点ID!"),
    UNUSABLE_SERVICE(10210, "无效的服务!"),
    INVALID_TOKEN(10211, "无效的用户令牌!"),
    DATA_CREATE_ERROR(10300, "数据创建失败"),
    DATA_DELETE_ERROR(10301, "数据删除失败"),
    DATA_UPDATE_ERROR(10302, "数据更新失败"),
    DATA_TRANSFORM_ERROR(10303, "数据转换失败");
    private final Integer code;
    private final String name;

    ESeResponseCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ESeResponseCode getEnum(int code) {
        for (ESeResponseCode type : ESeResponseCode.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

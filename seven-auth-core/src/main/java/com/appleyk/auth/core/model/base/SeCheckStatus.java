package com.appleyk.auth.core.model.base;

/**
 * <p>对象审状态</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-15:02
 */
public class SeCheckStatus {
    /**待审核*/
    public static final Integer PENDING = 0;
    /**已审核*/
    public static final Integer PASSED = 1;
    /**未通过*/
    public static final Integer NO_PASS = 2;
    /**已废除*/
    public static final Integer ABOLISHED  = 3;
}

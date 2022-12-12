package com.appleyk.auth.core.annotation;

import java.lang.annotation.*;

/**
 * <p>用户令牌校验注解</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午8:49 2022/12/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SeToken {
    /**可以带上权限值*/
    long[] purviews() default {};
}

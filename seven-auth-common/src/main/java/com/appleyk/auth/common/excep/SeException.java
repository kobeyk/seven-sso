package com.appleyk.auth.common.excep;

import com.appleyk.auth.common.core.SeResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>自定义异常基类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/17-15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeException extends Exception{
    protected int status;
    protected String message;
    public final SeResult buildResult() {
        return SeResult.builder().status(this.status).message(this.message).build();
    }
}

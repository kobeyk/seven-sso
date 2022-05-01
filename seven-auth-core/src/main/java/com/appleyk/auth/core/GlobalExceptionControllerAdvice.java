package com.appleyk.auth.core;

import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>全局Controller（接口）异常结果通知（拦截异常，返回统一状态和码值）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/15-8:47
 */
@CrossOrigin
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionControllerAdvice {
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity errorHandler(Exception ex) {
        if (ex instanceof SeException) {
            SeException exp = (SeException)ex;
            return "404".equals(exp.getStatus()) ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(SeResult.fail(404, ex.getMessage())) : ResponseEntity.ok(exp.buildResult());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SeResult.fail(-1, ex.getMessage()));
        }
    }
}

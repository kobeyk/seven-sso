package com.appleyk.auth.common.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * <p>封装http响应结果对象</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-9:43
 */
@Data
public class SeResult {
    private int status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8" )
    private Date timestamp;
    public SeResult(){
        this(200,"");
    }
    public SeResult(int status, String message) {
        this(status, message, (Object)null);
    }

    public SeResult(SeResult.Builder builder) {
        this(builder.status, builder.message, builder.data);
    }

    public SeResult(int status, String message, Object data) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static SeResult ok() {
        return (new SeResult.Builder()).status(ESeResponseCode.OK.getCode()).build();
    }

    public static SeResult ok(String message) {
        return (new SeResult.Builder()).status(ESeResponseCode.OK.getCode()).message(message).build();
    }

    public static SeResult ok(String message, Object data) {
        return (new SeResult.Builder()).status(ESeResponseCode.OK.getCode()).message(message).data(data).build();
    }

    public static SeResult fail(int status, String message, Object data) {
        return (new SeResult.Builder()).status(status).message(message).data(data).build();
    }

    public static SeResult fail(int status, String message) {
        return (new SeResult.Builder()).status(status).message(message).build();
    }

    public static SeResult.Builder builder() {
        return new SeResult.Builder();
    }

    /**内部静态builder*/
    public static class Builder {
        private int status;
        private String message;
        private Object data;
        public Builder() {
        }
        public SeResult.Builder status(int status) {
            this.status = status;
            return this;
        }
        public SeResult.Builder message(String message) {
            this.message = message;
            return this;
        }
        public SeResult.Builder data(Object data) {
            this.data = data;
            return this;
        }
        public SeResult build() {
            return new SeResult(this);
        }
    }
}

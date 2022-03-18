package com.appleyk.auth.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:18
 */
public class SeLoggerHelper {
    private static Logger logger = LoggerFactory.getLogger(SeLoggerHelper.class);
    public SeLoggerHelper() {
    }
    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void error(String message, Exception ex) {
        logger.error(message, ex);
    }

    public static void error(Integer errCode, String message) {
        logger.error("错误码：" + errCode + "，错误消息：" + message);
    }

    public static void error(String message) {
        logger.error("错误消息：" + message);
    }

    public static void error(Integer errCode, String message, Exception ex) {
        logger.error("错误码：" + errCode + "，错误消息：" + message + ",异常信息：" + ex.getMessage());
    }
}

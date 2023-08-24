package com.appleyk.auth.core.helper;

import com.appleyk.auth.core.container.SeSessionCacheBeanContainer;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.service.ASeSessionCache;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>越努力，越幸运</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:04 2022/12/12
 */
@Component
public class SeAuthHelper {
    private SeAuthHelper() {

    }

    /**
     * 获取当前登录的用户信息
     */
    public static SeAuthUser getCurrentUser() throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        SeAuthUser user = null;
        String token = getCurrentToken();
        if (!StringUtils.isEmpty(token)) {
            long userId = SeTokenHelper.decodeToken(token, "uid");
            String userName = SeTokenHelper.decodePayLoadToken(token, "userName");
            if (userId > 0L) {
                user = new SeAuthUser(userId, userName);
                request.setAttribute("authuser", user);
                return user;
            }
        }
        return null;
    }

    /**
     * 获取当前登录的用户ID
     */
    public static long getCurrentUserId() throws Exception {
        SeAuthUser authUser = getCurrentUser();
        return authUser != null ? authUser.getId() : 0L;
    }

    /**
     * 获取当前登录的用户令牌
     */
    public static String getCurrentToken() throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        if (token != null && !"".equals(token)) {
            ASeSessionCache sessionCache = SeSessionCacheBeanContainer.getSessionCache();
            return sessionCache.checkToken(token) == null ? null : token;
        } else {
            return null;
        }
    }
}

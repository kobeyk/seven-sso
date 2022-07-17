package com.appleyk.auth.core.service;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.container.SeSessionCacheBeanContainer;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.session.SeSsoInfo;

import java.util.Date;

/**
 * <p>抽象会话缓存类，统一实现token的check功能</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:59 2022/3/26
 */
public abstract class ASeSessionCache implements ISeSessionCache {

    public ASeSessionCache() {
        SeSessionCacheBeanContainer.addCacheBean(this.cacheName(), this);
    }

    public abstract String cacheName();

    @Override
    public SeSsoInfo checkToken(String token) throws SeException {
        if (SeGeneralUtils.isEmpty(token)){
            throw new SeCommonException(ESeResponseCode.INVALID_CLIENT, "用户认证失败！令牌不允许空！");
        }
        long userId;
        try {
            /**验证token合法性，同时解析出uid*/
            userId = SeTokenHelper.verifyToken(token);
        } catch (Exception e) {
            userId = 0L;
        }
        if (0 == userId) {
            throw new SeCommonException(ESeResponseCode.INVALID_TOKEN, "无效的用户令牌，获取用户信息失败！");
        }
        SeSsoInfo ssoInfo = get(userId);
        /**如果取出来的为空，表明令牌已过期了*/
        if (SeGeneralUtils.isEmpty(ssoInfo)) {
            throw new SeCommonException(ESeResponseCode.EXPIRED_TOKEN, "用户令牌已过期！");
        }
        String localToken = ssoInfo.getLocalToken();
        String clientToken = ssoInfo.getClientToken();
        if (token.equals(localToken) || token.equals(clientToken)){
            ssoInfo.setLastAccessTime(new Date());
            return ssoInfo;
        }else{
            throw new SeCommonException(ESeResponseCode.INVALID_TOKEN,"无效的用户令牌！");
        }
    }
}

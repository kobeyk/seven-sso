package com.appleyk.sso.server.service;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.container.SeAppSiteContainer;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.SeAppSite;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import org.springframework.stereotype.Service;

/**
 * <p>sso服务端实现认证管理，主要实现登录和登出业务逻辑</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午11:39 2022/5/1
 */
@Service
public class SeSsoAuthManager extends ASeAuthManager {
    @Override
    public SeSsoInfo doLogin(SeAuthUser authUser, Long appId) throws SeException {
        /**首先先检查缓存中是不是存在，如果存在的话，直接返回*/
        SeSsoInfo cacheSsoInfo = sessionCache().get(authUser.getId());
        if (SeGeneralUtils.isNotEmpty(cacheSsoInfo) && appId.equals(cacheSsoInfo.getAppId())) {
            if (SeGeneralUtils.isNotEmpty(appId) && SeGeneralUtils.isNotEmpty(cacheSsoInfo.getCallbackUrl())){
                return cacheSsoInfo;
            }
        }
        /**如果缓存不存在，则新生成token，然后缓存起来*/
        SeSsoInfo ssoInfo = new SeSsoInfo();
        ssoInfo.setUser(authUser);
        if (SeGeneralUtils.isNotEmpty(appId)) {
            ssoInfo.setAppId(appId);
            if (!SeAppSiteContainer.APP_SITES.containsKey(appId)) {
                throw new SeCommonException(ESeResponseCode.INVALID_APP_ID, "未经授权的无效的应用站点ID！");
            }
            SeAppSite appSite = SeAppSiteContainer.APP_SITES.get(appId);
            /**如果AppId存在服务端的缓存中，说明合法，那就取出回调地址，设置到ssoInfo中*/
            ssoInfo.setCallbackUrl(appSite.getCallbackUrl());
        }
        try {
            ssoInfo = SeTokenHelper.createToken(ssoInfo);
            /**将ssoInfo信息缓存起来*/
            sessionCache().put(authUser.getId(), ssoInfo);
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage(), e);
            throw new SeCommonException(ESeResponseCode.INVALID_CLIENT, "登录失败，用户令牌无法生成！");
        }
        return ssoInfo;
    }

    @Override
    public void doLogout(String token ,long uid) throws SeException {
        sessionCache().remove(uid);
    }

    @Override
    public SeSsoInfo checkToken(String token) throws SeException {
        long uid;
        SeSsoInfo ssoInfo;
        try {
            uid = SeTokenHelper.verifyToken(token);
            if (SeGeneralUtils.isEmpty(uid)) {
                throw new SeCommonException(ESeResponseCode.INVALID_CLIENT, "用户令牌不合法！");
            }
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage(), e);
            throw new SeCommonException(ESeResponseCode.INVALID_CLIENT, "用户令牌不合法！");
        }
        ssoInfo = sessionCache().checkToken(token);
        if (SeGeneralUtils.isEmpty(ssoInfo)) {
            throw new SeCommonException(ESeResponseCode.INVALID_CLIENT, "用户令牌不合法！");
        }
        return ssoInfo;
    }
}

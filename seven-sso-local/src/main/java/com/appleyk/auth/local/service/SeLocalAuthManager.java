package com.appleyk.auth.local.service;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import org.springframework.stereotype.Service;

/**
 * <p>local版认证管理实现</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:02 2022/5/2
 */
@Service
public class SeLocalAuthManager extends ASeAuthManager {

    /**
     * local版是不存在多个应用站点的，它是和应用系统深度集成在一起的，所以appId这个参数用不到的
     */
    @Override
    public SeSsoInfo doLogin(SeAuthUser authUser, Long appId) throws SeException {
        /**先判断缓存是不是有uid的session*/
        SeSsoInfo ssoInfo = sessionCache().get(authUser.getId());
        if (SeGeneralUtils.isNotEmpty(ssoInfo)) {
            return ssoInfo;
        }
        /**如果不存在，就创建token缓存*/
        ssoInfo = new SeSsoInfo(authUser);
        try {
            ssoInfo = SeTokenHelper.createToken(ssoInfo);
            sessionCache().put(authUser.getId(),ssoInfo);
            return ssoInfo;
        } catch (Exception e) {
            SeLoggerHelper.error(String.format("用户UID：%s,生成token异常 - %s", authUser.getId(), e.getCause()), e);
            throw new SeCommonException(ESeResponseCode.DATA_CREATE_ERROR, "用户令牌生成失败！");
        }
    }

    @Override
    public void doLogout(String token, long uid) throws SeException {
        sessionCache().remove(uid);
    }

    @Override
    public SeSsoInfo checkToken(String token) throws SeException {
        return sessionCache().checkToken(token);
    }

}

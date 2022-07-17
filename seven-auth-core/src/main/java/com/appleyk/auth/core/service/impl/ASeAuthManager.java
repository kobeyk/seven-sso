package com.appleyk.auth.core.service.impl;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.ids.SeIdProducer;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.common.util.SeMD5Encrypt;
import com.appleyk.auth.core.container.SeAuthListenerBeanContainer;
import com.appleyk.auth.core.container.SeSessionCacheBeanContainer;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ASeAuthListener;
import com.appleyk.auth.core.service.ASeSessionCache;
import com.appleyk.auth.core.service.ISeAuthManager;
import com.appleyk.auth.core.service.ISeAuthUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>抽象认证管理默认实现,固定算法，定义算法模板</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-16:47
 */
public abstract class ASeAuthManager implements ISeAuthManager {

    @Autowired
    private ISeAuthUser userService;

    @Override
    public SeAuthUser register(String userName, String password) throws SeException {
        SeAuthUser authUser = new SeAuthUser(SeIdProducer.getID(),userName,password);
        userService.save(authUser);
        authUser.setPassword(null);
        return authUser;
    }

    @Override
    public SeAuthUser register(String userName, String password, Map<String, Object> info) throws SeException {
        SeAuthUser authUser = new SeAuthUser(SeIdProducer.getID(),userName,password,info);
        userService.save(authUser);
        authUser.setPassword(null);
        return authUser;
    }

    @Override
    public SeSsoInfo login(String userName, String password, Long appId) throws SeException{
        SeAuthUser authUser = userService.findByName(userName);
        if (authUser == null) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID,"用户账号不存在");
        }
        try{
            if (!SeMD5Encrypt.valid(password, authUser.getPassword())) {
                throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID,"登录密码错误");
            }
        }catch (Exception e){
            SeLoggerHelper.error(e.getMessage(),e);
            throw new SeCommonException(e.getMessage());
        }
        authUser.setPassword(null);
        SeSsoInfo ssoInfo = doLogin(authUser,appId==null?0L:appId);
        doAfterLogin(authUser);
        return ssoInfo;
    }

    @Override
    public SeSsoInfo loginToken(String token) throws SeException{
        return new SeSsoInfo();
    }

    @Override
    public SeSsoInfo checkToken(String token) throws SeException{
        return new SeSsoInfo();
    }

    public SeAuthUser getUser(String token) throws SeException{
        return new SeAuthUser();
    }

    @Override
    public void verifyCode(String key, String code) throws SeException{
        String value = sessionCache().get(key);
        if (SeGeneralUtils.isEmpty(value)){
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST,"验证码不存在或已过期，请重新申请！");
        }
        if (!code.equals(value)){
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID,"验证码有误！");
        }
    }

    @Override
    public void logout(String token) throws SeException{
        /**1、验证token并解析出uid*/
        long uid;
        try{
            uid = SeTokenHelper.verifyToken(token);
        }catch (Exception e){
            throw new SeCommonException(ESeResponseCode.INVALID_CLIENT,"无效的用户令牌！");
        }
        /**2、查询认证用户*/
        SeAuthUser authUser = userService.findById(uid);
        /**3、登出前回调*/
        doBeforeLogout(authUser);
        /**4、真正的登出业务逻辑实现*/
        doLogout(token,uid);
    }

    /**子类需要实现登录的业务逻辑，比如缓存session*/
    public abstract SeSsoInfo doLogin(SeAuthUser authUser,Long appId) throws SeException;
    /**子类需要实现登出的业务逻辑，比如移除session*/
    public abstract void doLogout(String token,long uid) throws SeException;

    /**登录后回调*/
    public void doAfterLogin(SeAuthUser authUser){
        List<ASeAuthListener> listeners = getAuthListeners();
        if (listeners == null) return;
        for (ASeAuthListener listener : listeners) {
            listener.afterLogin(authUser);
        }
    }

    /**登录前回调*/
    public void doBeforeLogout(SeAuthUser authUser){
        List<ASeAuthListener> listeners = getAuthListeners();
        if (listeners == null) return;
        for (ASeAuthListener listener : listeners) {
            listener.beforeLogout(authUser);
        }
    }

    private List<ASeAuthListener> getAuthListeners() {
        List<ASeAuthListener> listeners = SeAuthListenerBeanContainer.getListeners();
        if (SeGeneralUtils.isEmpty(listeners)) {
            return null;
        }
        return listeners;
    }

    public ASeSessionCache sessionCache() throws SeException {
        ASeSessionCache sessionCache = SeSessionCacheBeanContainer.getSessionCache();
        if (sessionCache == null){
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST,"未发现可用的session缓存服务！");
        }
        return sessionCache;
    }
}

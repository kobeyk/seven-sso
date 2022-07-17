package com.appleyk.auth.client.service;

import com.appleyk.auth.client.service.rpc.SeTokenAuthClient;
import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.common.util.SeJsonUtils;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>客户端认证管理实现</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午10:03 2022/5/2
 */
@Service
public class SeClientAuthManager extends ASeAuthManager {

    @Autowired
    private SeTokenAuthClient authClient;

    @Override
    public SeSsoInfo doLogin(SeAuthUser authUser, Long appId) throws SeException {
        /**client模块不存在login业务，这里抛一个异常以示警醒！*/
        throw new SeCommonException(ESeResponseCode.UNUSABLE_SERVICE, "不可用的登录服务！");
    }

    @Override
    public void doLogout(String token, long uid) throws SeException {
        /**与认证服务端进行通信，告知对方token要"下线"了*/
        SeResult result = authClient.logout(token);
        /**如果请求成功*/
        if (ESeResponseCode.OK.getCode().equals(result.getStatus())) {
            sessionCache().remove(uid);
        } else {
            throw new SeCommonException("退出登录失败！");
        }
    }

    @Override
    public SeSsoInfo loginToken(String token) throws SeException {
        SeResult result = authClient.checkToken(token);
        if (ESeResponseCode.OK.getCode().equals(result.getStatus())) {
            /**缓存服务端session到应用系统缓存环境（如果是单机，建议直接使用默认的local模式，如果是集群，那必然使用redis）*/
            Object data = result.getData();
            SeSsoInfo ssoInfo;
            /**将data进行转换*/
            ObjectMapper mapper = new ObjectMapper();
            ssoInfo = mapper.convertValue(data, SeSsoInfo.class);
            if (SeGeneralUtils.isNotEmpty(ssoInfo)) {
                long uid = SeTokenHelper.decodeToken(token, SeTokenHelper.USER_ID);
                /**先将session缓存起来*/
                sessionCache().put(uid, ssoInfo);
                /**缓存成功后，处理登录回调方法*/
                doAfterLogin(ssoInfo.getUser());
                return ssoInfo;
            } else {
                SeLoggerHelper.error("client端请求服务端接口checkToken返回的对象内容为：" + SeJsonUtils.object2Json(data));
                throw new SeCommonException(ESeResponseCode.UNUSABLE_SERVICE, "无效的checkToken服务！");
            }
        } else {
            SeLoggerHelper.error("client端请求服务端接口checkToken返回的错误消息为：" + result.getMessage());
            throw new SeCommonException(ESeResponseCode.getEnum(result.getStatus()), result.getMessage());
        }
    }

    @Override
    public SeSsoInfo checkToken(String token) throws SeException {
        /**checkToken走应用系统中的session*/
        return sessionCache().checkToken(token);
    }

    @Override
    public SeAuthUser getUser(String token) throws SeException{
        SeResult result = authClient.getUser(token);
        if (ESeResponseCode.OK.getCode().equals(result.getStatus())) {
            Object data = result.getData();
            SeAuthUser user;
            /**将data进行转换*/
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.convertValue(data, SeAuthUser.class);
            if (SeGeneralUtils.isNotEmpty(user)){
               /**更新当前token中的session中的authUser*/
                Long uid = user.getId();
                SeSsoInfo ssoInfo = sessionCache().get(uid);
                BeanUtils.copyProperties(user,ssoInfo.getUser());
                sessionCache().put(uid,ssoInfo);
                return user;
            }else{
                SeLoggerHelper.error("client端请求服务端接口getUser返回的对象内容为：" + SeJsonUtils.object2Json(data));
                throw new SeCommonException(ESeResponseCode.UNUSABLE_SERVICE, "无效的getUser服务！");
            }
        }else{
            SeLoggerHelper.error("client端请求服务端接口checkToken返回的错误消息为：" + result.getMessage());
            throw new SeCommonException(ESeResponseCode.getEnum(result.getStatus()), result.getMessage());
        }
    }
}

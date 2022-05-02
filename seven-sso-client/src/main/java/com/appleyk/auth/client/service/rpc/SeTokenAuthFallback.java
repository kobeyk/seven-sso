package com.appleyk.auth.client.service.rpc;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.model.SeAuthUser;
import org.springframework.stereotype.Component;

/**
 * <p>容错处理类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午10:31 2022/5/2
 */
@Component
public class SeTokenAuthFallback implements SeTokenAuthClient {

    @Override
    public SeResult checkToken(String token) throws SeException{
        SeLoggerHelper.error("client端请求server端的checkToken失败，疑似server端接口不可用！");
        return SeResult.fail(ESeResponseCode.UNUSABLE_SERVICE.getCode(),"sso-server checkToken接口服务不可用！");
    }

    @Override
    public SeResult logout(String token) throws SeException {
        SeLoggerHelper.error("client端请求server端的logout失败，疑似server端接口不可用！");
        return SeResult.fail(ESeResponseCode.UNUSABLE_SERVICE.getCode(),"sso-server logout接口服务不可用！");
    }

    @Override
    public SeResult getUser(String token) throws SeException {
        SeLoggerHelper.error("client端请求server端的getUser失败，疑似server端接口不可用！");
        return SeResult.ok("请求成功！",new SeAuthUser());
    }
}



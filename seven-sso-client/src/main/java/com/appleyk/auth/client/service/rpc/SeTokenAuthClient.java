package com.appleyk.auth.client.service.rpc;

import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>openfeign 用户认证客户端接口配置</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午10:15 2022/5/2
 */
@FeignClient(name="sso-server", url="${se.sso.endpoint}", fallback = SeTokenAuthFallback.class)
public interface SeTokenAuthClient {

    @RequestMapping(value="/auth/checkToken",method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
    SeResult checkToken(@RequestHeader("token") String token) throws SeException;

    @RequestMapping(value="/auth/logout",method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
    SeResult logout(@RequestHeader("token") String token) throws SeException;

    @RequestMapping(value="/auth/getUser",method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
    SeResult getUser(@RequestHeader("token") String token) throws SeException;

}

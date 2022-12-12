package com.appleyk.client.app.controller;

import com.appleyk.auth.client.service.SeClientAuthManager;
import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.annotation.SeToken;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private SeClientAuthManager authManager;

    @GetMapping("hello")
    public String hello(){
        return "hello client !";
    }

    /**单点登录成功后，拿到token首先调用一下loginToken，目的就是缓存下token到业务系统的session缓存中*/
    @GetMapping("loginToken")
    public SeResult loginToken(@RequestHeader("token") String token) throws SeException {
        SeSsoInfo ssoInfo = authManager.loginToken(token);
        return SeResult.ok("登录成功！",ssoInfo);
    }

    /**加了@SeToken注解的方法，必须要对用户请求中的headers中的token进行校验*/
    @SeToken
    @GetMapping("getUsers")
    public SeResult getUsers(){
        List<SeAuthUser> users = new ArrayList<>();
        users.add(new SeAuthUser(1001L,"小美"));
        users.add(new SeAuthUser(1002L,"小帅"));
        users.add(new SeAuthUser(1003L,"小酷"));
        return SeResult.ok("查询成功!",users);
    }
}

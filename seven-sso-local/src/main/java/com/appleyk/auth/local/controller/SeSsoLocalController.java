package com.appleyk.auth.local.controller;

import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.controller.SeAuthBaseController;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.SeLoginUser;
import com.appleyk.auth.core.model.SeRegister;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>local模块接口控制器（接口功能几乎和sso服务端一模一样）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午9:57 2022/5/2
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class SeSsoLocalController extends SeAuthBaseController {

    @Autowired
    private ASeAuthManager authManager;

    @PostMapping("/register")
    public SeResult register(@RequestBody SeRegister register) throws SeException {
        return super.register(register);
    }

    @PostMapping("/login")
    public SeResult login(@RequestBody SeLoginUser loginUser) throws SeException {
        return super.login(loginUser);
    }

    @GetMapping("/logout")
    public SeResult logout(@RequestHeader String token) throws SeException {
        return super.logout(token);
    }

    @GetMapping("/checkToken")
    public SeResult checkToken(@RequestHeader String token) throws SeException {
        SeSsoInfo ssoInfo = authManager.checkToken(token);
        return SeResult.ok("验证通过！",ssoInfo.getUser());
    }

    /**
     * 基于原密码是设置新的密码
     */
    @PostMapping("/password/reset")
    public SeResult resetPwd(String userName, String password, String resetPwd, String reResetPwd) throws SeException {
        return super.resetPwd(userName, password, resetPwd, reResetPwd);
    }

    @PostMapping("/update")
    public SeResult update(@RequestBody SeAuthUser authUser) throws SeException {
        return SeResult.ok("更新成功！", updateUser(authUser));
    }

    @GetMapping("/getUser")
    public SeResult getUser(@RequestHeader String token) throws SeException {
        return super.getUser(token);
    }

    @GetMapping("/imageCode")
    public void getImageCode(String userName, HttpServletResponse response) throws SeException {
        imageCode(userName, response);
    }

    @GetMapping("/code")
    public SeResult getCode(String userName) throws SeException {
        return super.getCode(userName);
    }

}

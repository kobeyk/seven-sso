package com.appleyk.sso.server.controller;

import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.container.SeAppSiteContainer;
import com.appleyk.auth.core.controller.SeAuthBaseController;
import com.appleyk.auth.core.model.SeAppSite;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.SeLoginUser;
import com.appleyk.auth.core.model.SeRegister;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>单点登录用户认证中心接口（控制器）</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午9:42 2022/5/1
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class SeSsoAuthController extends SeAuthBaseController {

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

    /**
     * 服务端只有checkToken接口,没有loginToken接口
     */
    @GetMapping("/checkToken")
    public SeResult checkToken(@RequestHeader String token) throws SeException {
        return super.checkToken(token);
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

    /**
     * 基于token解析获取用户信息
     */
    @GetMapping("/getUser")
    public SeResult getUser(@RequestHeader String token) throws SeException{
        return super.getUser(token);
    }

    /**
     * 获取站点应用配置（内容不包含回调地址）
     */
    @GetMapping("/websites/pull")
    public SeResult getWebAppSitesConfig(){
        Map<Long, SeAppSite> appSites = SeAppSiteContainer.APP_SITES;
        if (SeGeneralUtils.isEmpty(appSites)) {
            return SeResult.ok("获取成功！", new ArrayList<>());
        }
        Collection<SeAppSite> values = appSites.values();
        Iterator<SeAppSite> iterator = values.iterator();
        List<SeAppSite> sites = new ArrayList<>();
        while (iterator.hasNext()) {
            SeAppSite appSite = iterator.next();
            appSite.setCallbackUrl(null);
            sites.add(appSite);
        }
        return SeResult.ok("获取成功！", sites);
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

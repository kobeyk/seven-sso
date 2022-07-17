package com.appleyk.auth.client.controller;

import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>客户端模块接口控制器</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  上午8:48 2022/5/2
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class SeClientAuthController {

    @Autowired
    private ASeAuthManager authManager;

    /***
     * 1. 首选拿token通过rpc（http，openfeign）调用sso服务端checkToken接口，验证token合法性
     * 2. 如果验证通过，sso服务端session续期的同时返回正确结果给client
     * 3. 如果验证不通过（令牌不合法、令牌过期等），sso服务端直接报错，应用系统处理（比如token过期，重新跳转到sso登录页面进行login）
     * 4. 如应用系统接收到sso服务端验证通过的消息后，紧接着就把token缓存到应用系统本地
     *    当然应用系统本地缓存也可以设置的和sso服务端一模一样，这样做的目的是为了减轻sso服务端checkToken的压力
     *    因为一旦应用系统很多后，业务接口也会变多，每一次业务接口请求前都要及时checkToken，如果每一次check走sso服务端（统一用户认证中心）
     *    的话，那sso-server的压力会激增，因此，可以将token的check分摊到各个应用系统，也就是将最终的压力交给缓存中间件！
     * 5. 别忘了重要的一点，只要登录，不管是通过用户名和密码登录，还是token登录，都要涉及登录后回调！
     */
    @GetMapping("/loginToken")
    public SeResult loginToken(@RequestHeader String token) throws SeException {
        SeSsoInfo ssoInfo = authManager.loginToken(token);
        return SeResult.ok("登录成功！", ssoInfo.getUser());
    }

    /***
     * 应用系统对token进行验证（验证的同时对token进行续期。此功能完全依赖于core模块）
     */
    @GetMapping("/checkToken")
    public SeResult checkToken(@RequestHeader String token) throws SeException {
        SeSsoInfo ssoInfo = authManager.checkToken(token);
        return SeResult.ok("验证成功!", ssoInfo.getUser());
    }

    /**
     * 1.全局退出，调用sso服务端的logout接口，成功后，一并清除应用系统缓存的token，这一步称作清除服务端session
     * 2.前端也要清除localStorage中的token，这一步称作清除浏览器端cookie
     */
    @GetMapping("/logout")
    public SeResult logout(@RequestHeader String token) throws SeException {
        authManager.logout(token);
        return SeResult.ok("退出登录成功！");
    }


    @GetMapping("/getUser")
    public SeResult getUser(@RequestHeader String token) throws SeException {
        SeAuthUser user = authManager.getUser(token);
        return SeResult.ok("获取用户信息成功!", user);
    }

}

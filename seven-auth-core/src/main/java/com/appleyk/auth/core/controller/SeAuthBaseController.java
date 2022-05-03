package com.appleyk.auth.core.controller;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.core.SeImageCode;
import com.appleyk.auth.common.core.SeResult;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.common.util.SeGeneralUtils;
import com.appleyk.auth.core.config.SeSsoProperties;
import com.appleyk.auth.core.dict.ESeCodeType;
import com.appleyk.auth.core.dict.ESeRegExp;
import com.appleyk.auth.core.helper.SeTokenHelper;
import com.appleyk.auth.core.model.SeAuthUser;
import com.appleyk.auth.core.model.SeLoginUser;
import com.appleyk.auth.core.model.SeRegister;
import com.appleyk.auth.core.model.session.SeSsoInfo;
import com.appleyk.auth.core.service.ISeAuthUser;
import com.appleyk.auth.core.service.impl.ASeAuthManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;

public class SeAuthBaseController {

    @Autowired
    private ASeAuthManager authManager;

    @Autowired
    private ISeAuthUser userService;

    @Autowired
    private SeSsoProperties ssoProperties;

    public SeResult getUser(String token) throws SeException {
        try {
            authManager.sessionCache().checkToken(token);
            long uid = SeTokenHelper.verifyToken(token);
            SeAuthUser authUser = userService.findById(uid);
            authUser.setPassword(null);
            return SeResult.ok("获取成功!", authUser);
        } catch (SeException se) {
            throw se;
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage(), e);
            throw new SeCommonException("获取用户信息失败！");
        }
    }

    public void imageCode(String userName, HttpServletResponse response) throws SeException {
        if (StringUtils.isEmpty(userName)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NAME_NOT_NULL, "请先输入用户名");
        }
        String code = authManager.sessionCache().get(userName);
        if (SeGeneralUtils.isNotEmpty(code)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_IS_EXIST, "验证码已存在，请勿频繁申请！");
        }
        /**1、构建图形码对象（每次调用都会随机产生一个text）*/
        SeImageCode imageCode = new SeImageCode();
        /** 2、从ValidCode类中获取BufferedImage对象；*/
        BufferedImage bufImage = imageCode.getImage();
        /** 3、同时获取验证码中的文本内容，并放到session域中， 用于校验；*/
        code = imageCode.getText();
        /** 4、缓存图形验证码*/
        authManager.sessionCache().put(userName, code);
        try {
            /** 5、将生成的图片输出到客户端浏览器*/
            imageCode.output(bufImage, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
    }

    public SeResult getCode(String userName) throws SeException {
        if (StringUtils.isEmpty(userName)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NAME_NOT_NULL, "请先输入用户名");
        }
        String code = authManager.sessionCache().get(userName);

        if (SeGeneralUtils.isEmpty(code)) {
            return SeResult.ok("获取成功！", "验证码已过期，请重新申请！");
        }
        return SeResult.ok("获取成功！", code);
    }

    public SeAuthUser updateUser(SeAuthUser authUser) throws SeException {
        SeAuthUser user = userService.update(authUser);
        SeSsoInfo ssoInfo = authManager.sessionCache().get(authUser.getId());
        /**替换变化的部分*/
        BeanUtils.copyProperties(user, ssoInfo.getUser());
        /**更新缓存*/
        authManager.sessionCache().put(authUser.getId(), ssoInfo);
        return user;
    }

    public SeResult register(SeRegister register) throws SeException {
        boolean verifyCode = ssoProperties.isVerifyCode();
        String userName = register.getName();
        if (SeGeneralUtils.isEmpty(userName)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "用户名不允许空！");
        }
        String password = register.getPassword();
        if (SeGeneralUtils.isEmpty(password)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "密码不允许空！");
        }
        if (!Pattern.matches(ESeRegExp.WEEK_PASSWORD.getRegular(), password)) {
            throw new SeCommonException(ESeResponseCode.INVALID_PASSWORD, ESeRegExp.WEEK_PASSWORD.getDescription());
        }
        String rePassword = register.getRePassword();
        if (SeGeneralUtils.isEmpty(rePassword)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "重复密码不允许空！");
        }
        if (!rePassword.equals(password)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "两次输入的用户密码不一致！");
        }
        ESeCodeType codeType = register.getCodeType();
        String code = register.getCode();
        /**如果注册开启了验证码，则检查注册对象中的验证码信息是否合法*/
        if (verifyCode) {
            if (SeGeneralUtils.isEmpty(codeType)) {
                throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID,
                        "验证模式（codeType）：1、短信码；2、图片码； 不允许空！");
            }
            if (SeGeneralUtils.isEmpty(code)) {
                throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "验证码不允许空！");
            }
            authManager.verifyCode(userName, code);
        }
        SeAuthUser authUser = authManager.register(userName, password);
        return SeResult.ok("用户注册成功！", authUser);
    }

    public SeResult login(SeLoginUser loginUser) throws SeException {
        String name = loginUser.getName();
        if (SeGeneralUtils.isEmpty(name)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "用户名不允许空！");
        }
        String password = loginUser.getPassword();
        if (SeGeneralUtils.isEmpty(password)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "用户密码不允许空！");
        }
        SeSsoInfo ssoInfo = authManager.login(name, password, loginUser.getAppId());
        return SeResult.ok("登录成功!", ssoInfo);
    }

    public SeResult logout(String token) throws SeException {
        if (SeGeneralUtils.isEmpty(token)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST, "令牌不允许为空！");
        }
        authManager.logout(token);
        return SeResult.ok("退出登录成功！");
    }

    public SeResult checkToken(String token) throws SeException {
        if (SeGeneralUtils.isEmpty(token)) {
            throw new SeCommonException(ESeResponseCode.OBJECT_NOT_EXIST, "令牌不允许为空！");
        }
        SeSsoInfo ssoInfo = authManager.checkToken(token);
        return SeResult.ok("用户令牌合法", ssoInfo);
    }

    public SeResult resetPwd(String userName, String password, String resetPwd, String reResetPwd) throws SeException {

        if (SeGeneralUtils.isEmpty(userName)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "用户名不能为空!");
        }
        if (SeGeneralUtils.isEmpty(password)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "旧密码不允许为空！");
        }
        if (SeGeneralUtils.isEmpty(resetPwd)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "新密码不允许为空！");
        }
        if (!Pattern.matches(ESeRegExp.WEEK_PASSWORD.getRegular(), resetPwd)) {
            throw new SeCommonException(ESeResponseCode.INVALID_PASSWORD, ESeRegExp.WEEK_PASSWORD.getDescription());
        }
        if (SeGeneralUtils.isEmpty(resetPwd)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "重置新密码不能为空！");
        }
        if (!resetPwd.equals(reResetPwd)) {
            throw new SeCommonException(ESeResponseCode.FILED_VALUE_INVALID, "两次输入的新密码不一致！");
        }
        if (!userService.resetPassword(userName, password, resetPwd)) {
            throw new SeCommonException("密码重置失败！");
        }
        return SeResult.ok("密码重置成功！");
    }
}

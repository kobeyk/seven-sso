window.server = {
    /**
     * 登录模式，local或sso
     * local模式无appId，且local模式下无需配置ssoUrl,只需要配置clientUrl即可
     */
    loginMode: "local",
    // 客户端（应用系统）服务地址
    clientUrl: "http://localhost:8082",
}
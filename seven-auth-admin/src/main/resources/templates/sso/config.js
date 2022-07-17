window.server = {
    /**
     * 登录模式，local或sso
     * local模式无appId，且local模式下无需配置ssoUrl
     */
    loginMode: "sso",
    // 单点登录（用户认证中心）服务地址
    ssoUrl: "http://localhost:8080/auth",
    // 客户端（应用系统）服务地址
    clientUrl: "http://localhost:8081",
    /**
     * 客户端在服务端的授权应用ID，该ID唯一，一个ID对应一个应用Application
     * 123456是单独运行前端项目调试用的appId,456321是admin发布后的appId
     * 其他AppId可在seven-sso-server模块下资源目录下的websites.xml中新增
     */
    appId: 456321,
}
const serverConfig = {
    // 单点登录（用户认证中心）服务地址
    ssoUrl: "http://localhost:8080/auth",
    // 客户端（应用系统）服务地址
    clientUrl: "http://localhost:8081",
    // 客户端在服务端的授权应用ID，该ID唯一，一个ID对应一个应用系统
    appId: 123456,
}
export default serverConfig;
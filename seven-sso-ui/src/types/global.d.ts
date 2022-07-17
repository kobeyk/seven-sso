export { };
declare global {
    interface Window {
        /** 服务配置 */
        server: {
            loginMode: string;
            ssoUrl: string;
            clientUrl: string;
            appId: number;
        }
    }
}
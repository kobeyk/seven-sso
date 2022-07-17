import { ISRegister } from './../typings/ISRegister';
import { SUser } from '../typings/SUser';
import axiosFn from '../utils/request';

/** 用户业务service，主要负责与后端接口服务进行”通信“ */
const userService = {
    /** 走sso-server认证中心的用户登录接口 */
    login: (user: SUser) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnPost("/login", user).then(res => {
                resolve(res)
            })
        })
    },
    /** 走sso-server认证中心的用户注册接口 */
    regist: (user: ISRegister) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnPost("/register", user).then(res => {
                resolve(res)
            })
        })
    },
    /** 走客户端模块中提供的用户登出接口 */
    logout: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet("/logout", {}).then(res => {
                resolve(res)
            })
        })
    },
    /** 走客户端模块中提供的接口，目的是让应用系统服务端缓存token */
    loginToken: (token: string) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet(`${window.server.clientUrl}/auth/loginToken`, {}, token).then(res => {
                resolve(res)
            })
        })
    },
    /** 使用客户端提供的接口，验证用户令牌的合法性 */
    checkToken: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet(`${window.server.clientUrl}/auth/checkToken`, {}).then(res => {
                resolve(res)
            })
        })
    },
    /** 使用客户端的提供的接口，获取用户信息 */
    getUser: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet(`${window.server.clientUrl}/auth/getUser`, {}).then(res => {
                resolve(res)
            })
        })
    },

}
export default userService;
import { ISRegister } from './../typings/ISRegister';
import { SUser } from '../typings/SUser';
import axiosFn from '../utils/request';
import serverConfig from '@/config/config';

const userService = {
    login: (user: SUser) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnPost("/login", user).then(res => {
                resolve(res)
            })
        })
    },
    regist: (user: ISRegister) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnPost("/register", user).then(res => {
                resolve(res)
            })
        })
    },
    logout: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet("/logout", {}).then(res => {
                resolve(res)
            })
        })
    },
    /** 使用客户端的，目的是让客户端的服务端缓存token */
    loginToken: (token: string) => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet(`${serverConfig.clientUrl}/auth/loginToken`, {}, token).then(res => {
                resolve(res)
            })
        })
    },
    /** 使用客户端的checkToken */
    checkToken: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet(`${serverConfig.clientUrl}/auth/checkToken`, {}).then(res => {
                resolve(res)
            })
        })
    },
    getUser: () => {
        return new Promise((resolve, reject) => {
            axiosFn.commonOnGet("/getUser", {}).then(res => {
                resolve(res)
            })
        })
    },

}
export default userService;
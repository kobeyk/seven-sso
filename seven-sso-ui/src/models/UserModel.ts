import { message } from 'antd';
import { ISRegister } from '@/typings/ISRegister';
import { SLoginUser } from '@/typings/SLoginUser';
import { SUser } from '@/typings/SUser';
import { Effect, Subscription } from "dva";
import userService from '@/services/UserService';
// import serverConfig from '@/config/config';
// import { routerRedux } from "dva";
import GeneralUtil from '@/typings/util/GeneralUtils';

export interface IUserState {
    user: SUser;
    appId: number;
    /** 
     * client模式下，appId正常传，登录时服务端会返回clientToken
     * local模式下， appId不传，登录时服务端会返回localToken
     */
    token: string;
    /** 登录成功的应用前端路由回调地址，仅在client模式下有效 */
    callbackUrl: string;
}
export interface IActionType {
    type: string;
    payload: SLoginUser | ISRegister | string | boolean;
}
export interface IUserModelType {
    namespace: string;
    state: IUserState,
    reducers: {
        saveSession: (state: IUserState, { payload }: { payload: IUserState }) => IUserState;
        saveUser: (state: IUserState, { payload }: { payload: SUser }) => IUserState;
        setLoginStatus: (state: IUserState, { payload }: { payload: boolean }) => IUserState;
    },
    effects: {
        login: ({ payload }: { payload: SLoginUser },
            { call, put }: { call: Function, put: Function }) => void;
        register: ({ payload }: { payload: ISRegister },
            { call, put }: { call: Function, put: Function }) => void;
        getUser: Effect;
        logout: Effect;
        checkToken: Effect,
        loginToken: Effect,
    },
    subscriptions: {
        setUp: Subscription;
    }
}
const UserModel: IUserModelType = {
    namespace: 'user',
    state: {
        user: new SUser(),
        appId: 0,
        token: "",
        callbackUrl: "",
    },
    reducers: {
        saveSession(state, { payload }) {
            return {
                ...state,
                user: payload.user,
                appId: payload.appId,
                token: payload.token,
                callbackUrl: payload.callbackUrl
            }
        },
        saveUser(state, { payload }) {
            return {
                ...state,
                user: payload as SUser
            }
        },
        setLoginStatus(state, { payload }) {
            return {
                ...state,
                bLogin: payload as boolean
            }
        }
    },
    effects: {
        *login({ payload }, { call, put }) {
            let res = yield call(userService.login, payload)
            message.success(res.message);
            let token = res.data.clientToken ? res.data.clientToken : res.data.localToken;
            yield put({
                type: "saveSession",
                payload: {
                    user: res.data.user,
                    token: token,
                    callbackUrl: res.data.callbackUr
                }
            })
            /** 第一种方式：当前页面打开URL页面并将token带过去,即回调地址（等同于路由跳转）*/
            window.location.href = res.data.callbackUrl + "?token=" + token;
            /** 第二种方式：使用routerRedux跳转 */
            // yield put(routerRedux.push("/"));
        },

        *register({ payload }, { call, put }) {
            const res = yield call(userService.regist, payload);
            message.success(res.message);
            GeneralUtil.redirectLogin();
        },

        *logout({ payload }, { call, put }) {
            let res = yield call(userService.logout);
            message.success(res.message)
            /** 移除浏览器缓存的token */
            GeneralUtil.removeToken();
            /** 重定向到登录页面 */
            GeneralUtil.redirectLogin();
        },

        *checkToken({ payload }, { call, put }) {
            if (!GeneralUtil.hasToken()) {
                message.error("用户令牌缺失，请前往登录！")
                GeneralUtil.redirectLogin();
                return;
            }
            let res = yield call(userService.checkToken)
            /** 验证token成功后，设置user */
            yield put({
                type: "saveUser",
                payload: res.data
            })
        },

        *loginToken({ payload }, { call, put }) {
            yield call(userService.loginToken, payload)
            GeneralUtil.setToken(payload)
            /** 同时验证下token */
            yield put({ type: 'checkToken' })
            /** 对单点登录成功后的回调地址进行处理，截断token，只要url部分 */
            let url = window.location.href.split('?')[0];
            /** html5新增的history api，作用就是浏览器地址栏变化，但是页面不重新载入 */
            window.history.pushState(null, "", url);
        },

        *getUser({ payload }, { call, put }) {
            /*** 不写了，意义不大 */
        }
    },

    subscriptions: {
        setUp({ dispatch, history }) {
            /** 全局路由监听 */
            history.listen(location => {
                /** 登录和注册页面无需验证token */
                if (location.pathname === '/login' || location.pathname === '/regist') {
                    return;
                }
                // let loginMode = serverConfig.loginMode; // config.js不放在public目录下的写法
                let loginMode = window.server.loginMode; // config.js放在public目录下的写法
                if ("local" === loginMode) {
                    dispatch({ type: 'checkToken' });
                } else if ("sso" === loginMode) {
                    let search = location.search;
                    if (search.length > 0) {
                        let token = GeneralUtil.getSearchParamValue(location.search, "token");
                        /** 如果是sso登录模式的话，login成功后拿到token要先调业务系统的loginToken进行缓存 */
                        if (token) {
                            dispatch({ type: 'loginToken', payload: token });
                        }
                    } else {
                        dispatch({ type: 'checkToken' });
                    }
                }
            })
        }
    }
}

export default UserModel;
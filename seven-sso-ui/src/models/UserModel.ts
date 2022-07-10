import { message, Select } from 'antd';
import { ISRegister } from './../typings/ISRegister';
import { SLoginUser } from './../typings/SLoginUser';
import { SUser } from '@/typings/SUser';
import { Effect, Subscription } from "dva";
import userService from '../services/UserService';
// import { routerRedux } from "dva";
import GeneralUtil from '@/utils/GeneralUtil';

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
    bLogin: boolean;
}
export interface IActionType {
    type: string;
    payload: SLoginUser | ISRegister | string | boolean;
}
export interface IUserModelType {
    namespace: string;
    state: IUserState,
    reducers: {
        saveSession: (state: IUserState, payload: IUserState) => IUserState;
        saveUser: (state: IUserState, payload: SUser) => IUserState;
        setLoginStatus: (state: IUserState, payload: boolean) => IUserState;
    },
    effects: {
        login: ({ payload }: { payload: SLoginUser },
            { call, put }: { call: Function, put: Function }) => void;
        register: Effect;
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
        bLogin: false,
    },
    reducers: {
        saveSession(state, payload) {
            return {
                ...state,
                user: payload.user,
                appId: payload.appId,
                token: payload.token,
                callbackUrl: payload.callbackUrl
            }
        },
        saveUser(state, payload) {
            return {
                ...state,
                user: payload as SUser
            }
        },
        setLoginStatus(state, payload) {
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

        },
        *logout({ payload }, { call, put }) {

        },
        *checkToken({ payload }, { call, put, select }) {
            let bLogin = yield select((state: IUserState) => state.bLogin);
            if (!bLogin) {
                return;
            }
            if (!GeneralUtil.hasToken()) {
                message.error("用户令牌缺失，请前往登录！")
                GeneralUtil.redirectLogin();
                return;
            }
            let res = yield call(userService.checkToken)
            if (res && res.status === 200) {
                yield put({
                    type: "setUser",
                    payload: res.user
                })
            }
        },
        *loginToken({ payload }, { call, put }) {
            yield call(userService.loginToken)
        },
        *getUser({ payload }, { call, put }) {

        }
    },
    subscriptions: {
        setUp({ dispatch, history }) {
            history.listen(location => {
                if (location.pathname === '/login' || location.pathname === '/regist') {
                    return;
                }
                dispatch({ type: 'checkToken' });
            })
        }
    }
}

export default UserModel;
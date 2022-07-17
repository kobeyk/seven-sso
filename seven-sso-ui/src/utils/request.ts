import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { message } from 'antd';
import GeneralUtil from '@/typings/util/GeneralUtils';

/** 创建axios实例 */
const apiService = axios.create({
    baseURL: window.server.ssoUrl,
    timeout: 30 * 1000,
    headers: {
        "Content-Type": "application/json",
    },
})

const toFormData = (option: any) => {
    let formData = new FormData();
    for (let key in option) {
        formData.append(key, option[key]);
    }
    return formData;
};

/** 配置请求拦截器 */
apiService.interceptors.request.use(
    /** 配置config，config里面包含了请求头的设置 */
    (config: AxiosRequestConfig) => {
        let token = localStorage.getItem("token");
        /** 如果token不空的话，设置到请求头中 */
        if (token) {
            config.headers!["token"] = token;
        }
        return config;
    },
    (err: any) => {
        message.error(err.message);
        return Promise.reject(err.messge);
    }
);
/** 配置响应拦截器 */
apiService.interceptors.response.use(
    (res: AxiosResponse) => {
        const result = res.data;
        const status = result.status;
        if (200 === status) {
            return Promise.resolve(result);
        } else if (10211 === status) {
            message.error("无效的用户令牌，请重新登录！")
            GeneralUtil.redirectLogin();
        } else if (10203 === status) {
            message.error("用户令牌已过期，请重新登录！")
            GeneralUtil.redirectLogin();
        } else {
            message.error(result.message);
            return Promise.reject(result);
        }
    },
    (err: any) => {
        if (err.response && err.response.data) {
            message.error(err.response.data.message);
        }
        return Promise.reject(err.messge);
    }
);

/** 统一定义axios请求方法格式，封装成一个函数 */
const axiosFn = {
    // get请求
    commonOnGet: (url: string, params: any, token: string = "") => {
        let config;
        if (GeneralUtil.isEmpty(token)) {
            /** 不带token */
            config = { params }
        } else {
            /** 带token */
            config = { headers: { "token": token }, params };
        }
        return apiService.get(url, config);
    },

    // post请求
    commonOnPost: (url: string, params: any, bFormData: boolean = false) => {
        if (bFormData) {
            params = toFormData(params);
        }
        return apiService.post(url, params);
    },

    // put请求
    commonOnPut: (url: string, params: any, bFormData: boolean = false) => {
        if (bFormData) {
            params = toFormData(params);
        }
        return apiService.put(url, params);
    },

    // delete请求
    commonOnDelete: (url: string, params: any) => {
        return apiService.delete(url, params);
    },

    // delete请求通过/id删除
    commonOnDeleteById: (url: string, id: number) => {
        return apiService.delete(url + "/" + id);
    },

    // 上传文件
    commonOnUpLoad: (url: string, params: any, bFormData: boolean = true) => {
        let header = { headers: { "Content-Type": "multipart/form-data" } };
        if (bFormData) {
            params = toFormData(params);
        }
        return apiService.post(url, params, header);
    },
};

export default axiosFn;


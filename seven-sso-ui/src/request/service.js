/*
 * @Descripttion: 创建axios实例
 * @version: v0.1.1
 * @Author: Appleyk
 * @Blob: https://blog.csdn.net/Appleyk
 * @GitHub: https://github.com/kobeyk
 * @Date: 2022-05-04 10:55:03
 * @LastEditors: Appleyk
 * @LastEditTime: 2022-05-04 17:40:31
 */
import axios from "axios";
import { serverUrl } from '../config.js'
import { message } from 'antd'

// 创建axios实例并做基础配置
const axiosService = axios.create(
    {
        baseURL: serverUrl.baseURL,
        timeout: 30 * 1000
    }
)

axiosService.defaults.headers.post["Content-Type"] = "application/json"

// 添加请求拦截器
axiosService.interceptors.request.use(
    config => {
        let token = localStorage.getItem('token')
        if (token) {
            // 设置token
            config.headers['token'] = token
        } else {
            // 跳转到登录页面
            window.location.href = serverUrl.loginUrl
        }
        return config;
    },
    error => {
        return Promise.reject(error)
    }
)

// 添加响应拦截器
axiosService.interceptors.response.use(
    /** res 是 axios封装的对象，其對象中的data屬性才是后台接口响应的真实返回結果 */
    res => {
        let status = res.status
        /** 对结果的status值进行判断，如果200就正常resolve，如果不是，做处理 */
        if (200 === status) {
            message.success(res.message)
            /** 成功了就抛出去，让业务组件再去调用then方法处理结果数据 */
            return Promise.resolve(res);
        } else {
            message.error(res.message)
            return Promise.reject(res.message);
        }
    },
    err => {
        message.error(err.message)
        return Promise.reject(err)
    }
)

/** 暴露axios实例，export default 后面只能跟一个 */
export default axiosService;
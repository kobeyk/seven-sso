/*
 * @Descripttion: 请求传参处理，按类型分为get、post、delete，注意这个js只处理参数
 * @version: v0.1.1
 * @Author: Appleyk
 * @Blob: https://blog.csdn.net/Appleyk
 * @GitHub: https://github.com/kobeyk
 * @Date: 2022-05-04 17:41:06
 * @LastEditors: Appleyk
 * @LastEditTime: 2022-05-04 17:47:09
 */
import axiosService from "./service";

const axiosFn = {
    commonOnGet(url, params) {
        return axiosService.get(url, { params })
    },
    commonOnPost(url, data) {
        return axiosService.post(url, data)
    },
    commonOnDelete(url, id) {
        return axiosService.delete(`${url}/${id}`)
    }
}
/** 暴露axios常用请求的封装对象（get、post、delete） */
export default axiosFn;

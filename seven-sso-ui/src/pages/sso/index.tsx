/*
 * @Descripttion: 
 * @version: v0.1.1
 * @Author: Appleyk
 * @Blob: https://blog.csdn.net/Appleyk
 * @GitHub: https://github.com/kobeyk
 * @Date: 2022-05-04 18:23:56
 * @LastEditors: Appleyk
 * @LastEditTime: 2022-05-04 22:27:23
 */
import Login from '@/components/login/Login'
import './index.less'
export default function SsoForm() {
    const bLogin = true
    const bRegister = false
    return (
        <div>
            <div className='sso-form'>
                <div className='sso-form-header'>
                    <div className='sso-form-header-login'>
                        <div className={bLogin ? 'form-login-selected' : 'form-login'}>登录</div>
                    </div>
                    <div className='sso-form-header-register'>
                        <div className={bRegister ? 'form-register-selected' : 'form-register'}>注册</div>
                    </div>
                </div>
                <Login />
            </div>
        </div>
    )
}

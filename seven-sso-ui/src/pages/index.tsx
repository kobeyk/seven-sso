/*
 * @Descripttion: 
 * @version: v0.1.1
 * @Author: Appleyk
 * @Blob: https://blog.csdn.net/Appleyk
 * @GitHub: https://github.com/kobeyk
 * @Date: 2022-05-03 15:36:16
 * @LastEditors: Appleyk
 * @LastEditTime: 2022-05-04 22:44:49
 */
// import './index.less';
import SsoForm from './sso';
import logo from '../assets/icon/logo.png'
import './index.less'
export default function IndexPage() {
  return (
    <div className='sso-layout'>
      <div className='sso-header'>
        <div className='sso-header-title'>统一用户认证中心</div>
        <div className='sso-header-logo'>
          <img src={logo} alt="logo" />
        </div>
      </div>
      <div className='sso-content'>
        <div className='sso-bg' />
        <SsoForm />
      </div>
      <div className='sso-footer'>作者：Appleyk</div>
    </div>
  );
}

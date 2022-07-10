import logo from "@/assets/images/login-logo.png";
const UserHeader = () => {
  return (
    <div className="user-header">
      <img src={logo} alt="logo" />
      <span>七仔单点登录</span>
    </div>
  )
}
export default UserHeader;
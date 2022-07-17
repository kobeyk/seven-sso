import { FC, ReactElement } from 'react';
import { RouteComponentProps } from "react-router-dom";
import UserForm from "@components/UserForm";
import UserHeader from "@components/UserHeader";
import "../user.scss";

/**
 * 用户登录组件
 */
const UserLogin: FC<RouteComponentProps> = ({
  history
}): ReactElement => {
  return (
    <div className="user-login-regist">
      <UserHeader />
      <UserForm title="用户登录" bLogin={true} history={history} />
    </div>
  );
};

export default UserLogin;

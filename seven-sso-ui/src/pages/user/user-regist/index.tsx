import { FC, ReactElement } from "react";
import { RouteComponentProps } from "react-router-dom";
import UserForm from "@components/UserForm";
import UserHeader from "@components/UserHeader";
import "../user.scss";

const UserRegist: FC<RouteComponentProps> = ({
  history
}): ReactElement => {
  return (
    <div className="user-login-regist">
      <UserHeader />
      <UserForm title="用户注册" bLogin={false} history={history} />
    </div>
  );
};

export default UserRegist;

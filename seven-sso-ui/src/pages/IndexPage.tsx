import React, { FC, ReactElement } from 'react'
import { useEffect } from 'react';
import { connect, Dispatch } from "dva";
import { IUserState } from '../models/UserModel';
import { SUser } from '@/typings/SUser';
import "./index.scss";
import { Button } from 'antd';

interface IProps {
    user: SUser,
    dispatch: Dispatch
}
const IndexPage: FC<IProps> = ({
    user, dispatch
}): ReactElement => {

    useEffect(() => {
    }, [])

    const logout = () => {
        dispatch({
            type: "user/logout"
        });
    }

    return (
        <div className="index-page">
            <div>
                <h1>应用系统AppId：{window.server.appId}</h1>
                <h1>
                    <span>用户ID:{user.id}</span>
                </h1>
                <h1>
                    <span>用户名：{user.name}</span>
                </h1>
                <h1>
                    <span>昵称：{user.alias}</span>
                </h1>
                <hr />
                <Button type="primary" onClick={logout}>退出登录</Button>
            </div>

        </div>
    )

}
const mapState2Props = (state: any) => {
    const { user }: { user: IUserState } = state;
    return {
        user: user.user,
    }
}
export default connect(mapState2Props)(IndexPage)
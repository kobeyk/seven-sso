import { Button } from 'antd'
import React, { FC, ReactElement } from 'react'
import serverConfig from "../config/config";
import { useEffect } from 'react';
import userService from '@services/UserService';
import { connect, Dispatch } from "dva";
import { IUserState } from '../models/UserModel';
import { SUser } from '@/typings/SUser';
import GeneralUtil from '@/utils/GeneralUtil';


interface IProps {
    user: SUser,
    bLogin: boolean,
    history: any,
    dispatch: Dispatch
}
const IndexPage: FC<IProps> = ({
    user, bLogin, history, dispatch
}): ReactElement => {
    useEffect(() => {
        let token = GeneralUtil.getSearchParamValue(history.location.search, "token");
        GeneralUtil.setToken(token);
        console.log(22, bLogin);
        if (!bLogin) {
            /** 调用loginToken */
            userService.loginToken().then((res: any) => {
                if (res) {
                    dispatch({
                        type: "user/setLoginStatus",
                        payload: true
                    })
                }
            })
        }

    }, [])
    const toLogin = () => {
        history.push(`/login?appId=${serverConfig.appId}`)
    }
    return (
        <div className="index-page">
            IndexPage
            <Button onClick={toLogin} type="primary">跳转</Button>
        </div>
    )
}
const mapState2Props = (state: any) => {
    const { user }: { user: IUserState } = state;
    return {
        user: user.user,
        bLogin: user.bLogin
    }
}
export default connect(mapState2Props)(IndexPage)
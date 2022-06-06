import { Button } from 'antd'
import React, { FC, ReactElement } from 'react'
interface IProps {
    history: any
}
const IndexPage: FC<IProps> = ({
    history
}): ReactElement => {
    const toLogin = () => {
        history.push("/login")
    }
    return (
        <div className="index-page">
            IndexPage
            <Button onClick={toLogin} type="primary">跳转</Button>
        </div>
    )
}

export default IndexPage
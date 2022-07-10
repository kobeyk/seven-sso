import React, { FC, ReactElement } from 'react';
import notFound from "../../assets/images/404.png";
import "./404.scss";

interface IProps {
  history: any;
}

const NotFound: FC<IProps> = ({
  history
}): ReactElement => {
  const path2Home = () => {
    history.push("/");
  };
  return (
    <div className="not-found">
      <img onClick={path2Home} src={notFound} alt="404" />
    </div>
  )
}

export default NotFound;
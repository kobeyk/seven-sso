
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Form, Input } from "antd";
import { FC, ReactElement, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { connect, Dispatch } from "dva";
import GeneralUtil from "@/typings/util/GeneralUtils";
import serverConfig from "@/config/config";
import { SLoginUser } from "@/typings/SLoginUser";

interface IUserContentProps {
  /** 表单标题 */
  title: string;
  /** 是否是登录（功能）还是注册（功能） */
  bLogin: boolean;
  /** 路由history对象，用于登录成功跳转 */
  history: any;
  dispatch: Dispatch;
}

/**
 * 登录和注册共用的表单组件
 */
const UserForm: FC<IUserContentProps> = ({
  bLogin,
  title,
  history,
  dispatch
}): ReactElement => {

  /** antd4.x 不再使用createForm包裹组件了，这里直接使用钩子函数 */
  const [form] = Form.useForm();

  /** hooks,函数式组件的state写法，类似于后端的setter */
  const [nameValidRules, setNameRules] = useState<any>([]);
  const [passwordValidRules, setPasswordRules] = useState<any>([]);
  const [rePasswordValidRules, setRePasswordRules] = useState<any>([]);

  /** 副作用钩子函数，类似于类组件中的componentDidMount生命周期函数 */
  useEffect(() => {
    if (bLogin) {
      // 如果登录的话，直接提示用户输入用户名，无需使用正则验证用户名合法性
      setNameRules([
        { required: true, message: "Please input your Username!" },
      ]);
      // 如果登录的话，直接提示用户输入密码，无需使用正则验证用户密码合法性
      setPasswordRules([
        { required: true, message: "Please input your Password!" },
      ]);
    } else {
      // 如果注册的话，需要验证用户输入的用户名是否符合系统规定
      setNameRules([
        {
          required: true,
          // 自定义验证用户名校验规则
          validator: validUserName,
        },
      ]);

      // 如果注册的话，需要验证用户输入的密码是否符合系统安全规定
      setPasswordRules([
        {
          required: true,
          // 自定义验证用户密码校验规则
          validator: validPassword,
        },
      ]);

      // 如果注册的话，验证下用户先后两次输入的密码是否正确
      setRePasswordRules([
        {
          required: true,
          validator: validRePassword,
        },
      ]);
    }
  }, [bLogin]);

  /** 表单提交函数，这里通过拿到form的values然后调用后端接口 */
  const onFinish = () => {
    /** 这里调用的不一样，login走的是登录，regist走的是注册 */
    if (bLogin) {
      form
        .validateFields()
        .then((values: any) => {
          const { search } = history.location
          let appId = GeneralUtil.getSearchParamValue(search, "appId");
          let user = new SLoginUser(values)
          if (appId) {
            user.appId = Number(appId);
          }
          /** 调用单点登录系统（用户统一认证中心服务）的用户登录接口 */
          dispatch({
            type: "user/login",
            payload: user.toData()
          })
        })
    } else {
      form
        .validateFields()
        .then((values: any) => {
          /** 调用单点登录系统（用户统一认证中心服务）的用户注册接口 */
          dispatch({
            type: "user/register",
            payload: values
          })
        })
        .catch((error: any) => {
          return Promise.reject(error);
        });
    }
  };

  /**
   * 验证用户输入的用户名是否符合系统规定
   * @param rule 验证规则对象
   * @param value 验证表单值
   * @returns 异步处理的回调结果
   */
  const validUserName = (rule: any, value: string) => {
    let regExp1 = /^([A-Za-z0-9])+@+([A-Za-z0-9])+.+([A-Za-z]{2,4})$/;
    let regExp2 = /^[a-zA-Z0-9]{2,20}$/;
    if (!value) {
      value = "";
    }
    if (
      value.length >= 2 &&
      value.length <= 20 &&
      (regExp1.test(value) || regExp2.test(value))
    ) {
      /** 新版antd4.x，callback已经废弃，这里请使用promise来替代*/
      return Promise.resolve();
    } else {
      return Promise.reject("用户名是2-20位的数字或字母或邮箱！");
    }
  };

  /**
   * 验证用户输入的密码是否符合安全规定
   * @param rule 验证规则对象
   * @param value 验证表单值
   * @returns 异步处理的回调结果
   */
  const validPassword = (rule: any, value: string) => {
    let regExp = /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{8,20}$/;
    if (!value) {
      value = "";
    }
    if (value.length > 0) {
      if (regExp.test(value)) {
        return Promise.resolve();
      } else {
        return Promise.reject("长度为8-20位的数字字母或特殊字符。");
      }
    } else {
      return Promise.reject("长度为8-20位的数字字母或特殊字符。");
    }
  };

  /**
   * 验证确认密码
   * @param rule 验证规则对象
   * @param value 验证表单值
   * @returns 异步处理的回调结果
   */
  const validRePassword = (rule: any, value: string) => {
    let regExp = /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{8,20}$/;
    if (!value) {
      value = "";
    }
    if (value.length > 0) {
      if (regExp.test(value)) {
        if (value && value !== form.getFieldValue("password")) {
          return Promise.reject("两次密码不一样!");
        } else {
          return Promise.resolve();
        }
      } else {
        return Promise.reject("长度为8-20位的数字字母或特殊字符。");
      }
    } else {
      return Promise.reject("长度为8-20位的数字字母或特殊字符。");
    }
  };

  return (
    <div className="user-content">
      <div className="user-form">
        <div className="form-title">{title}</div>
        <Form
          form={form}
          name="normal_login"
          initialValues={{ remember: true }}
          onFinish={onFinish}
        >
          <Form.Item name="name" rules={nameValidRules}>
            <Input
              prefix={<UserOutlined className="site-form-item-icon" />}
              placeholder="请输入用户名"
            />
          </Form.Item>
          <Form.Item name="password" rules={passwordValidRules}>
            <Input
              prefix={<LockOutlined className="site-form-item-icon" />}
              type="password"
              placeholder="请输入密码"
              autoComplete="true"
            />
          </Form.Item>
          {bLogin ? null : (
            <Form.Item name="rePassword" rules={rePasswordValidRules}>
              <Input
                prefix={<LockOutlined className="site-form-item-icon" />}
                type="password"
                placeholder="请输入确认密码"
                autoComplete="true"
              />
            </Form.Item>
          )}
          <Form.Item>
            <Button
              type="primary"
              block
              htmlType="submit"
              className="login-form-button"
            >
              {bLogin ? "登录" : "注册"}
            </Button>
            <div className="form-footer">
              {bLogin ? (
                <Link to="/regist">立即注册？</Link>
              ) : (
                <Link to={`/login?appId=${serverConfig.appId}`}>立即登录？</Link>
              )}
            </div>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default connect()(UserForm);

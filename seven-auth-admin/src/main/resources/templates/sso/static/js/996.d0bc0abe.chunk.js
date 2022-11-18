"use strict";(self.webpackChunkseven_sso_ui=self.webpackChunkseven_sso_ui||[]).push([[996],{3018:function(e,s,r){r.d(s,{Z:function(){return B}});r(1071);var A=r(435),t=(r(149),r(7006)),i=(r(616),r(6361)),o=r(9439),a=r(9529),l=r(8999),n=r(2791),c=r(1523),u=r(364),m=r(4736),d={loginMode:"sso",ssoUrl:"http://localhost:8080/auth",clientUrl:"http://localhost:8081",appId:123456},w=r(3144),g=r(5671),j=r(136),I=r(4062),Z=function(e){(0,j.Z)(r,e);var s=(0,I.Z)(r);function r(e){var A;return(0,g.Z)(this,r),(A=s.call(this,e)).appId=void 0,A.password=void 0,A.appId=e&&e.appId||null,A.password=e&&e.password||"",A}return(0,w.Z)(r)}(r(7690).Z),p=r(184),B=(0,u.$j)()((function(e){var s=e.bLogin,r=e.title,u=e.history,w=e.dispatch,g=i.Z.useForm(),j=(0,o.Z)(g,1)[0],I=(0,n.useState)([]),B=(0,o.Z)(I,2),z=B[0],x=B[1],E=(0,n.useState)([]),h=(0,o.Z)(E,2),f=h[0],M=h[1],G=(0,n.useState)([]),X=(0,o.Z)(G,2),H=X[0],S=X[1];(0,n.useEffect)((function(){s?(x([{required:!0,message:"Please input your Username!"}]),M([{required:!0,message:"Please input your Password!"}])):(x([{required:!0,validator:C}]),M([{required:!0,validator:V}]),S([{required:!0,validator:v}]))}),[s]);var C=function(e,s){return s||(s=""),s.length>=2&&s.length<=20&&(/^([A-Za-z0-9])+@+([A-Za-z0-9])+.+([A-Za-z]{2,4})$/.test(s)||/^[a-zA-Z0-9]{2,20}$/.test(s))?Promise.resolve():Promise.reject("\u7528\u6237\u540d\u662f2-20\u4f4d\u7684\u6570\u5b57\u6216\u5b57\u6bcd\u6216\u90ae\u7bb1\uff01")},V=function(e,s){return s||(s=""),s.length>0&&/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{8,20}$/.test(s)?Promise.resolve():Promise.reject("\u957f\u5ea6\u4e3a8-20\u4f4d\u7684\u6570\u5b57\u5b57\u6bcd\u6216\u7279\u6b8a\u5b57\u7b26\u3002")},v=function(e,s){return s||(s=""),s.length>0&&/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{8,20}$/.test(s)?s&&s!==j.getFieldValue("password")?Promise.reject("\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u6837!"):Promise.resolve():Promise.reject("\u957f\u5ea6\u4e3a8-20\u4f4d\u7684\u6570\u5b57\u5b57\u6bcd\u6216\u7279\u6b8a\u5b57\u7b26\u3002")};return(0,p.jsx)("div",{className:"user-content",children:(0,p.jsxs)("div",{className:"user-form",children:[(0,p.jsx)("div",{className:"form-title",children:r}),(0,p.jsxs)(i.Z,{form:j,name:"normal_login",initialValues:{remember:!0},onFinish:function(){s?j.validateFields().then((function(e){var s=u.location.search,r=m.Z.getSearchParamValue(s,"appId"),A=new Z(e);r&&(A.appId=Number(r)),w({type:"user/login",payload:A.toData()})})):j.validateFields().then((function(e){w({type:"user/register",payload:e})})).catch((function(e){return Promise.reject(e)}))},children:[(0,p.jsx)(i.Z.Item,{name:"name",rules:z,children:(0,p.jsx)(t.Z,{prefix:(0,p.jsx)(a.Z,{className:"site-form-item-icon"}),placeholder:"\u8bf7\u8f93\u5165\u7528\u6237\u540d"})}),(0,p.jsx)(i.Z.Item,{name:"password",rules:f,children:(0,p.jsx)(t.Z,{prefix:(0,p.jsx)(l.Z,{className:"site-form-item-icon"}),type:"password",placeholder:"\u8bf7\u8f93\u5165\u5bc6\u7801",autoComplete:"true"})}),s?null:(0,p.jsx)(i.Z.Item,{name:"rePassword",rules:H,children:(0,p.jsx)(t.Z,{prefix:(0,p.jsx)(l.Z,{className:"site-form-item-icon"}),type:"password",placeholder:"\u8bf7\u8f93\u5165\u786e\u8ba4\u5bc6\u7801",autoComplete:"true"})}),(0,p.jsxs)(i.Z.Item,{children:[(0,p.jsx)(A.Z,{type:"primary",block:!0,htmlType:"submit",className:"login-form-button",children:s?"\u767b\u5f55":"\u6ce8\u518c"}),(0,p.jsx)("div",{className:"form-footer",children:s?(0,p.jsx)(c.rU,{to:"/regist",children:"\u7acb\u5373\u6ce8\u518c\uff1f"}):(0,p.jsx)(c.rU,{to:"/login?appId=".concat(d.appId),children:"\u7acb\u5373\u767b\u5f55\uff1f"})})]})]})]})})}))},7945:function(e,s,r){r.d(s,{Z:function(){return t}});var A=r(184),t=function(){return(0,A.jsxs)("div",{className:"user-header",children:[(0,A.jsx)("img",{src:"data:image/png;base64,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",alt:"logo"}),(0,A.jsx)("span",{children:"\u4e03\u4ed4\u5355\u70b9\u767b\u5f55"})]})}},6996:function(e,s,r){r.r(s);var A=r(3018),t=r(7945),i=(r(774),r(184));s.default=function(e){var s=e.history;return(0,i.jsxs)("div",{className:"user-login-regist",children:[(0,i.jsx)(t.Z,{}),(0,i.jsx)(A.Z,{title:"\u7528\u6237\u6ce8\u518c",bLogin:!1,history:s})]})}},774:function(){}}]);
//# sourceMappingURL=996.d0bc0abe.chunk.js.map
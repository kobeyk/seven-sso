/**
 * @api {POST} /register 1 用户注册
 * @apiName register
 * @apiGroup 用户认证中心
 * @apiDescription 注册用户
 * @apiParam {String} name 用户名
 * @apiParam {String} password 用户密码
 * @apiParam {String} rePassword 用户重复密码
 * @apiParamExample params-Json:
 * {
 * "name":"appleyk",
 * "password":"123456a",
 * "rePassword":"123456a"
 * }
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "用户注册成功！",
"data": {
"id": 1509771861875712,
"name": "appleyk",
"checkStatus": 1,
"alias": null,
"avatar": null,
"info": null,
"cTime": "2022-07-10 13:40:20",
"uTime": "2022-07-10 13:40:20"
},
"timestamp": "2022-07-10 13:40:20"
}
 * @apiSuccessExample {json} Fail-Response:
{
"status": 10102,
"message": "用户名已被占用！",
"timestamp": "2022-07-10 13:41:10"
}
 */

/**
 * @api {POST} /login 2 用户登录
 * @apiName login
 * @apiGroup 用户认证中心
 * @apiDescription 用户登录
 * @apiParam {String} name 用户名
 * @apiParam {String} password 用户密码
 * @apiParam {Number} appId 单点登录系统（用户统一认证中心）给应用站点授权的ID，只有client模式下传这个参数才有用，local模式下无需传appId

 * @apiParamExample params-Json:
{
"name":"appleyk",
"password":"123456a",
"appId":123456
}
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "登录成功!",
"data": {
"user": {
"id": 1509771861875712,
"name": "appleyk",
"checkStatus": 1,
"alias": "appleyk",
"avatar": null,
"info": {},
"cTime": "2022-07-10 13:40:20",
"uTime": "2022-07-10 13:40:20"
},
"appId": 123456,
"clientToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOjEyMzQ1NiwidWlkIjoxNTA5NzcxODYxODc1NzEyLCJpc3MiOiJodHRwczovL2dpdGh1Yi5jb20va29iZXlrIiwidXNlck5hbWUiOiJhcHBsZXlrIiwiaWF0IjoxNjU3NDMyNDA3fQ.iFcKa30Zyu2Z5xYRHVwQ-3xGPWBD4X8qzv_lBsr2tn4",
"callbackUrl": "http://localhost:3020/#/",
"lastAccessTime": "2022-07-10 13:53:27"
},
"timestamp": "2022-07-10 13:53:27"
}
 * @apiSuccessExample {json} Fail-Response-1:
{
"status": 10207,
"message": "未经授权的无效的应用站点ID！",
"timestamp": "2022-07-10 13:59:06"
}
 * @apiSuccessExample {json} Fail-Response-2:
{
"status": 400,
"message": "登录密码错误",
"timestamp": "2022-07-10 14:15:24"
}
 */

/**
 * @api {GET} /checkToken 3 验证用户令牌
 * @apiName checkToken
 * @apiGroup 用户认证中心
 * @apiDescription 注册用户
 * @apiHeader {String} token 用户令牌（服务端会在客户端登录成功后返回token）
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "用户令牌合法",
"data": {
"user": {
"id": 1509771861875712,
"name": "appleyk",
"checkStatus": 1,
"alias": "appleyk",
"avatar": null,
"info": {},
"cTime": "2022-07-10 13:40:20",
"uTime": "2022-07-10 13:40:20"
},
"appId": 123456,
"clientToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHAiOjEyMzQ1NiwidWlkIjoxNTA5NzcxODYxODc1NzEyLCJpc3MiOiJodHRwczovL2dpdGh1Yi5jb20va29iZXlrIiwidXNlck5hbWUiOiJhcHBsZXlrIiwiaWF0IjoxNjU3NDMyNDA3fQ.iFcKa30Zyu2Z5xYRHVwQ-3xGPWBD4X8qzv_lBsr2tn4",
"callbackUrl": "http://localhost:3020/#/",
"lastAccessTime": "2022-07-10 14:03:32"
},
"timestamp": "2022-07-10 14:03:32"
}
 * @apiSuccessExample {json} Fail-Response:
{
"status": 10201,
"message": "用户令牌不合法！",
"timestamp": "2022-07-10 14:03:55"
}
 */

/**
 * @api {POST} /password/reset 4 修改密码
 * @apiName reSetPassword
 * @apiGroup 用户认证中心
 * @apiDescription 用户在已知原有旧密码基础上设置新的登录密码。
 * @apiParam {String} name 用户名
 * @apiParam {String} password 用户原密码
 * @apiParam {String} resetPwd 用户新密码
 * @apiParam {String} reResetPwd 用户重复新密码
 * @apiParamExample params-formData:
 name = appleyk
 password = 123456a
 resetPwd = Aa123456
 reResetPwd = Aa123456
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "密码重置成功！",
"timestamp": "2022-07-10 14:12:22"
}
 * @apiSuccessExample {json} Fail-Response:
{
"status": 10001,
"message": "原密码不对！",
"timestamp": "2022-07-10 14:10:01"
}
 */

/**
 * @api {POST} /update 5 设置基本信息
 * @apiName updateInfo
 * @apiGroup 用户认证中心
 * @apiDescription 用户设置基本信息，如昵称、头像等
 * @apiParam {Number} id 用户ID
 * @apiParam {String} name 用户密码
 * @apiParam {String} alias 用户昵称
 * @apiParam {String} avatar 用户头像
 * @apiParamExample params-Json:
{
"id": 1509771861875712,
"name": "appleyk",
"alias": "山治",
"avatar": "http://icon.com"
}
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "更新成功！",
"data": {
"id": 1509771861875712,
"name": "appleyk",
"checkStatus": 1,
"alias": "山治",
"avatar": "http://icon.com",
"info": null,
"cTime": "2022-07-10 13:40:20",
"uTime": "2022-07-10 14:17:04"
},
"timestamp": "2022-07-10 14:17:04"
}
 * @apiSuccessExample {json} Fail-Response:
{
"status": 10101,
"message": "当前用户不存在！",
"timestamp": "2022-07-10 14:17:33"
}
 */

/**
 * @api {GET} /getUser 6 获取用户信息
 * @apiName getUserInfo
 * @apiGroup 用户认证中心
 * @apiDescription 通过用户令牌查询对应的用户信息
 * @apiHeader {String} token 用户令牌

 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "获取成功!",
"data": {
"id": 1509771861875712,
"name": "appleyk",
"checkStatus": 1,
"alias": "山治",
"avatar": "http://icon.com",
"info": {},
"cTime": "2022-07-10 13:40:20",
"uTime": "2022-07-10 13:40:20"
},
"timestamp": "2022-07-10 14:21:35"
}
 * @apiSuccessExample {json} Fail-Response:
{
"status": 10211,
"message": "无效的用户令牌，获取用户信息失败！",
"timestamp": "2022-07-10 14:21:55"
}
 */

/**
 * @api {GET} /getUser 7.1 获取图形验证码
 * @apiName getImageCode
 * @apiGroup 用户认证中心
 * @apiDescription 通过用户名获取对应的图形码，仅在用户注册时使用。
 * @apiParam {String} name 用户名
 * @apiParamExample 基于用户名获取图形验证码:
 * {{ssoUrl}}/auth/imageCode?userName=appleyk
 */

/**
 * @api {GET} /getUser 7.2 获取验证码
 * @apiName getCode
 * @apiGroup 用户认证中心
 * @apiDescription 通过用户名获取对应字符串形式的验证码，仅在用户注册时使用。
 * @apiParam {String} name 用户名
 * @apiParamExample 基于用户名获取字符串验证码:
 * {{ssoUrl}}/auth/code?userName=appleyk
 * @apiSuccessExample {json} Success-Response:
 * {
 *     "status": 200,
 *     "message": "获取成功！",
 *     "data": "1JaE",
 *     "timestamp": "2022-07-10 14:45:25"
 * }
 */

/**
 * @api {GET} /logout 8 用户退出
 * @apiName logout
 * @apiGroup 用户认证中心
 * @apiDescription 基于用户令牌进行用户退出。
 * @apiHeader {String} token 用户令牌
 * @apiSuccessExample {json} Success-Response:
 * {
 *     "status": 200,
 *     "message": "退出登录成功！",
 *     "timestamp": "2022-07-10 14:55:42"
 * }
 */

/**
 * @api {GET} /logout 9 拉取所有应用站点信息
 * @apiName pullWebsites
 * @apiGroup 用户认证中心
 * @apiDescription 获取所有授权的应用站点配置信息。
 * @apiSuccessExample {json} Success-Response:
{
"status": 200,
"message": "获取成功！",
"data": [
{
"id": 123456,
"serviceUrl": "http://localhost:3020/#/",
"createTime": "2022-04-30T20:00:00.000+00:00",
"updateTime": "2022-04-30T15:00:00.000+00:00"
}
],
"timestamp": "2022-07-10 15:03:00"
}
 */


# seven-sso

#### 介绍


```
    用7这个数字，是因为我儿子是七夕生的。开源的sso框架有很多，公司的、个人的，优秀的很多。那为什么我又要写个呢？
因为对单点登录的流程太过于熟悉了，以至于太想把自己的想法通过代码的方式给呈现出来，而且还是开源出来供大家参考学习。

    本项目模块化鲜明，配置极度灵活，Session缓存的实现方式考虑的很周到，基本上使用者只需要简单配置一下就可以轻松
实现Session的缓存功能。项目的模块配置也是极力遵循SpringBoot提出的"约定大于配置"，采用了自动装配技术，从而省去
了使用者引入相应模块需要做过多bean扫描配置的时间。同时，项目配套的文档齐全：如项目模块图、项目软件设计、项目单点
登录流程（序列）图、项目接口文档、项目配套前端测试站点等。

    时序图有时间了，我随后就补上，总之就是，看懂了时序图，代码就没什么难度了，整个项目所有的资源文件全部在根目录
下的src/main下的resources下，包括接口文档源文件（可二次修改，基于apidoc自动生成）、数据库脚本、ea建模设计文件、
效果图及后续的时序图（分client和local两个模块）等。
```

#### 项目技术栈

##### 后端
- SpringBoot 2.2.2.RELEASE 
- Mybatis 3.4.6 
- Tk.Mybatis.SpringBoot 2.1.5
- PageHelper.SpringBoot 1.2.10
- Jedis 3.1.0
- Expiringmap 0.5.9
- JWT 3.8.0
- PostgreSql 42.2.5
- Lombok 1.18.10
##### 前端
- React 17.0.2
- DvaJs 2.6.0-beta.22
- React-Router-Dom v5 (dva模块中自带) 
- AntD 4.21.0
- Axios 0.27.2
- TypeScript 4.4.2
- Craco 6.4.3 (打包模块)

#### 项目模块图

![Seven-SSO模块图](https://gitee.com/appleyk/seven-sso/raw/master/src/main/resources/static/images/1.modules.png)

#### 项目核心业务模块设计

![Seven-SSO模块图](https://gitee.com/appleyk/seven-sso/raw/master/src/main/resources/static/images/3.design.png)

#### 项目接口文档

![Seven-SSO接口文档](https://gitee.com/appleyk/seven-sso/raw/master/src/main/resources/static/images/2.apidoc.jpg)

#### 安装教程

1.  git clone xxxxx 
2.  mvn clean package -DskipTests=true
3.  分别启动seven-sso-server、seven-auth-client和seven-auth-admin模块服务

#### 使用说明

没有什么好说的，跑一遍代码然后看下源码调一调跟一跟就看懂了，很简单的。

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
5.  如果你提交的request对项目有用，我会merge的



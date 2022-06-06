
import dynamic from 'dva/dynamic';
import { Redirect, Route, Router, Switch } from 'dva/router';
import loginRoutes from '@routes/loginRoutes';
import indexRoutes from '@routes/indexRoutes';

// 合并所有路由配置 ,注意v5版本路由匹配是有顺序的，index路由配置要往后放
const allRouter = [...loginRoutes, ...indexRoutes]

/**
 * 递归实现路由的方法，dynamics可动态加载路由，即使用时才加载
 * @param {*} routes 一组路由配置（外部拆分，内部合并，数组）
 * @param {*} app 
 * @returns <Route/>
 */
function mapRouteMethod(routes, app) {
    return routes.map(({ path, children, ...dynamics }, index) => {
        if (children) {
            return <Route key={index} path={path} render={(props) => {
                const Component = dynamic({ app, ...dynamics })
                return (<Component {...props}> {mapRouteMethod(children, app)} </Component>)
            }} />
        }
        return <Route key={index} exact path={path} component={dynamic({ app, ...dynamics })} />
    })
}

// 路由组件
function RouterComponent({ history, app }) {
    return (
        <Router history={history}>
            <Switch>
                {mapRouteMethod(allRouter, app)}
                {/* 都不匹配的话,跳转到404页面 */}
                <Redirect to="/404" />
            </Switch>
        </Router>
    )
}

export default RouterComponent

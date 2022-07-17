import { router } from "dva";
import { dynamic } from "dva";
import userRoutes from '@routes/userRoutes';
import indexRoutes from '@routes/indexRoutes';

/**
 * <p>DvaJS+RRD·v5版本的静态路由配置转换为全局路由组件经典实现</p>
 * @author appleyk
 * @date 2022年7月2日22:52:55
 */

// 合并所有路由配置 ,注意v5版本路由匹配是有顺序的，index路由配置要往后放
const allRouters = [...userRoutes, ...indexRoutes]
const { Redirect, Route, Switch, Router } = router;

/**
 * 递归实现路由组件的方法，dva的dynamics方法可动态加载路由组件，即使用时才加载
 * @param {*} routes 路由配置对象（外部拆分，内部合并）
 * @param {*} app
 * @returns <Route/> 路由组件
 */
function mapRouteMethod(routes, app) {

    /**
     * 解构routes对象的几个参数解释
     * path : 路由相对地址
     * to   : 路由地址不匹配是跳转的地址
     * exact: 路由地址是否精准匹配（凡是嵌套路由，子路由地址一定要精准匹配）
     * children : 路由是否嵌套，如果嵌套，children不为空
     * ...dynamics: 解构懒加载方式的组件导入方式，如() => import("@pages/xxxxx/xxxxx")
     */
    return routes.map(({ path, to, exact, children, ...dynamics }, index) => {
        if (children) {
            return (
                <Route
                    key={index}
                    path={path}
                    render={(props) => {
                        /** 对路由组件进行懒加载包装 */
                        const Component = dynamic({ app, ...dynamics });
                        return (
                            /** 这个地方一定要用Switch包裹一下递归的嵌套子路由，以实现嵌套路由地址的单一匹配 */
                            <Component {...props}>
                                <Switch>
                                    {mapRouteMethod(children, app)}
                                </Switch>
                            </Component>
                        );
                    }}
                />
            );
        }

        /** 如果path等于*说明匹配不到当前路由url了，那就走重定向 */
        return path !== "*" ? (
            <Route
                key={index}
                exact={exact}
                path={path}
                component={dynamic({ app, ...dynamics })}
            />
        ) : (
            <Redirect key={index} from="*" to={to} />
        );
    });
}

// 路由组件
function RouterComponent({ history, app }) {
    return (
        <Router history={history}>
            <Switch>
                {mapRouteMethod(allRouters, app)}
                {/* 内层都不匹配的话,最外层跳转到404页面 */}
                <Redirect to="/404" />
            </Switch>
        </Router>
    );
}

export default RouterComponent;

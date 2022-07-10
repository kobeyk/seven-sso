const userRoutes = [
    {
        path: "/login",
        exact: true,
        name: "登录",
        component: () => import("@pages/user/user-login"),
    },
    {
        path: "/regist",
        name: "注册",
        exact: true,
        component: () => import("@pages/user/user-regist"),
    },
    {
        path: "/404",
        name: "404",
        component: () => import("@pages/not-found"),
    },
]
export default userRoutes;
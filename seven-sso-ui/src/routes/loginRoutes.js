const loginRoutes = [
    {
        path: "/login",
        name: "登录",
        component: () => import("@pages/login"),
    }
]
export default loginRoutes;
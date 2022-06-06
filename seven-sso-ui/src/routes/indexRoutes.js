const indexRoutes = [
    {
        path: "/",
        name: "首页",
        component: () => import("@pages/IndexPage"),
    }
]
export default indexRoutes;
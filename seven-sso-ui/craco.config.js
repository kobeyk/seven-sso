const path = require("path");
const resolve = dir => path.resolve(__dirname, dir);
const CracoLessPlugin = require("craco-less");
module.exports = {
    // antd定义主题色
    plugins: [
        {
            plugin: CracoLessPlugin,
            options: {
                lessLoaderOptions: {
                    lessOptions: {
                        //自定义主题色(绿色)
                        modifyVars: { "@primary-color": "green" },
                        javascriptEnabled: true,
                    },
                },
            },
        },
    ],
    //antd组件js按需引入（比如只用到了antd的Button按钮，但是打包时却引入了整个antd模块）
    babel: {
        plugins: [
            [
                "import",
                {
                    libraryName: "antd",
                    libraryDirectory: "es",
                    style: true,
                },
            ],
        ],
    },
    webpack: {
        alias: {
            "@": resolve("src"),
            "@components": resolve("src/components"),
            "@assets": resolve("src/assets"),
            "@typings": resolve("src/typings"),
            "@utils": resolve("src/utils"),
            "@pages": resolve("src/pages"),
            "@routes": resolve("src/routes"),
            "@models": resolve("src/models"),
            "@services": resolve("src/services"),
        }
    }
};

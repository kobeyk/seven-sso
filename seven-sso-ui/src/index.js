import dva from "dva";
const app = new dva({})
app.use({})
app.model(require("@models/UserModel").default)
app.router(require("./router").default)
app.start("#root")
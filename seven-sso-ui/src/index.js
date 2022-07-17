import dva from "dva";
import "@assets/styles/public.scss";
const app = new dva({})
app.use({})
app.model(require("@models/UserModel").default)
app.router(require("./router").default)
app.start("#root")
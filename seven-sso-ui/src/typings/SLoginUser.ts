import SObejct from "./SObject";
export class SLoginUser extends SObejct {
    appId?: number;
    password: string;
    constructor(options?: any) {
        super(options);
        this.appId = (options && options.appId) || null;
        this.password = (options && options.password) || "";
    }
}
import SObejct from "./SObject";
export class SUser extends SObejct {
    alias: string;
    avatar: string;
    info: Map<string, object>;
    cTime: string;
    uTime: string;
    constructor(options?: any) {
        super(options);
        this.alias = (options && options.alias) || "";
        this.avatar = (options && options.avatar) || "";
        this.info = (options && options.info) || null;
        this.cTime = (options && options.cTime) || null;
        this.uTime = (options && options.uTime) || null
    }
}
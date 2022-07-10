import GeneralUtil from "../utils/GeneralUtil";
export default class SObejct {
    id?: number;
    name: string;
    constructor(options: any) {
        if (options && options.id) {
            this.id = options.id;
        }
        this.name = (options && options.name) || "";
    }

    /** 统一处理空值 */
    public toData() {
        return GeneralUtil.processData(this)
    }
}
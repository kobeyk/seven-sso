export default class GeneralUtil {
    static isEmpty = (obj: any): boolean => {
        if (typeof obj === 'undefined' || obj === null) {
            return true;
        } else {
            return false;
        }
    }
    static processData = (obj: any): any => {
        /* 删除空值 */
        Object.keys(obj).forEach(key => {
            if (GeneralUtil.isEmpty(obj[key])) {
                delete obj[key];
            }
        })
        return obj;
    }
    static getSearchParamValue = (search: string, property: string): any => {
        let searchObj = new URLSearchParams(search);
        return searchObj ? searchObj.get(property) : null;
    }

    static hasToken = () => {
        let token = localStorage.getItem("token");
        return token !== null && token !== "undifined";
    }

    static redirectLogin = () => {
        setTimeout(() => {
            window.location.href =
                window.location.origin + window.location.pathname + `#/login?appId=${window.server.appId}`;
        }, 300);
    };

    static setToken = (token: string): void => {
        if (token) {
            localStorage.setItem("token", token);
        }
    }

    static getToken = () => {
        return localStorage.getItem("token");
    }

    static removeToken = () => {
        localStorage.removeItem("token");
    }
}

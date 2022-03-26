package com.appleyk.auth.common.ids;

/**
 * <p>
 *     雪花ID生成器
 *     (这里简单起见，没有注入machineId和dataCenterId，
 *     如果服务搭配负载，且存在批量插入的业务需求的话，一定要确保每个服务实例上的
 *     machineId和dataCenterId不一致，以防止id碰撞，当前业务中的雪花算法最大支持的
 *     服务实例可达7*7=49
 *     )
 * </p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:17
 */
public class SeIdProducer {

    private static volatile SeIdMaker idWorker = null;
    private SeIdProducer(){

    }
    public static synchronized Long getID() {
        if(null == idWorker){
            synchronized (SeIdProducer.class){
                if(idWorker == null){
                    idWorker = new SeIdMaker();
                }
            }
        }
        return idWorker.nextId();
    }
}

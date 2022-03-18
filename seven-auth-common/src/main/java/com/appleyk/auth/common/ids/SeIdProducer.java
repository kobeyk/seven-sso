package com.appleyk.auth.common.ids;

/**
 * <p>雪花ID生成器</p>
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

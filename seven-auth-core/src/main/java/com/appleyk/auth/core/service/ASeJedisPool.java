package com.appleyk.auth.core.service;

import com.appleyk.auth.common.core.ESeResponseCode;
import com.appleyk.auth.common.excep.SeCommonException;
import com.appleyk.auth.common.excep.SeException;
import com.appleyk.auth.common.helper.SeLoggerHelper;
import com.appleyk.auth.core.container.SeRedisInstanceContainer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.*;

/**
 * <p>抽象redis客户端对象操作</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午10:58 2022/4/2
 */
public abstract class ASeJedisPool {
    public ASeJedisPool(){
        SeRedisInstanceContainer.addJedisPool(mode(),this);
    }
    protected byte[] serialize(Object obj){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos =  new ObjectOutputStream(baos)){
            oos.writeObject(obj);
            return baos.toByteArray();
        }catch (Exception e){
            SeLoggerHelper.error(e.getMessage(), e);
        }
        return null;
    }
    protected Object unSerialize(byte[] data){
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            SeLoggerHelper.error(e.getMessage(), e);
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                SeLoggerHelper.error(e.getMessage(), e);
            }
        }
        return null;
    }
    /**redis服务是否可用*/
    public boolean isAvailable() throws SeException{
        try{
            exists(mode());
            return true;
        }catch (JedisConnectionException connE){
            SeLoggerHelper.error("Redis connect failure.Please check your address,port number and the password.");
            throw new SeCommonException(ESeResponseCode.UNUSABLE_SERVICE,connE.getMessage());
        } catch (Exception e){
            SeLoggerHelper.error(e.getMessage());
            throw new SeCommonException(ESeResponseCode.UNUSABLE_SERVICE,e.getMessage());
        }
    }
    public abstract String mode();
    /**redis 的 setx命令是原子操作，即保证设置值和key的过期时间两个动作在同一时间内完成*/
    public abstract String setex(String key,int seconds,String value);
    /**获取key对应的值*/
    public abstract String get(String key);
    public abstract Long expire(String key,int seconds);
    /**判断key是否存在*/
    public abstract boolean exists(String key);
    /**序列化key对应的object值，二进制存入reids*/
    public abstract String setObject(String key,int seconds,Object value);
    /**反序列化key对应的二进制数据，拿到对象*/
    public abstract Object getObject(String key);
    /**移除key*/
    public abstract long remove(String key);
}

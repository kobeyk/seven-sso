package com.appleyk.auth.core.container;

import com.appleyk.auth.core.service.ASeAuthListener;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>认证监听bean容器，收集应用系统中所有实现了认证监听器的实例bean</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/23-16:03
 */
public class SeAuthListenerBeanContainer {
    /**监听器有序，先来先执行*/
    private static List<ASeAuthListener> listeners = new LinkedList<>();
    private SeAuthListenerBeanContainer(){}
    public static void addListener(int order,ASeAuthListener authListener) {
        if (authListener == null){
            throw new RuntimeException("authListener is null !");
        }
        synchronized (listeners){
            int size = listeners.size();
            String listenerName = authListener.listenerName();
            boolean exists = listeners.stream().filter(l -> listenerName.equals(l.listenerName())).count() > 0;
            if (exists){
                throw new RuntimeException(String.format("listener name - %s already exist !",listenerName));
            }
            int index;
            if (order < 0){
                index = 0;
            }else if (order >= size ){
                index = size;
            }else{
                index = order;
            }
            listeners.add(index,authListener);
        }
    }
    public static List<ASeAuthListener> getListeners(){
        return listeners;
    }
}

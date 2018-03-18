package com.dongnao.client;

import com.google.common.reflect.Reflection;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class ServiceProxyFactory {
    static Object getService(String serviceName) throws ClassNotFoundException{
        Class<?> interfaceType=Class.forName(serviceName);
        return Reflection.newProxy(interfaceType,new ServiceProxy(serviceName));
    }
}

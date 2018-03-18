package com.dongnao.client;

import com.dongnao.common.entity.RemoteRequest;
import com.dongnao.common.entity.RemoteRespone;
import com.google.common.reflect.AbstractInvocationHandler;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class ServiceProxy extends AbstractInvocationHandler {
    private String serviceName;
    public ServiceProxy(String serviceName){
        this.setServiceName(serviceName);
    }
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        ProviderInfo providerInfo =ServiceProviderManager.getProvider(serviceName);
        RemoteRequest remoteRequest=new RemoteRequest();
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setServiceName(serviceName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setArguments(objects);

        RemoteClient remoteClient=new RemoteClient(providerInfo);

        RemoteRespone remoteRespone= remoteClient.send(remoteRequest);
        return remoteRespone.getResponeValue();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}

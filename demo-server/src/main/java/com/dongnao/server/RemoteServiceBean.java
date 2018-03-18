package com.dongnao.server;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class RemoteServiceBean {

    private String serviceName;
    private Object serviceImpl;


    public void init(){
        RemoteServer.addServices(serviceName,serviceImpl);
        System.out.println("服务："+serviceName+" 已经注册上来");
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }
}

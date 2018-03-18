package com.dongnao.client;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class ServiceProviderManager {
    private ProviderInfo providerInfo;
    public  static ProviderInfo  getProvider(String servceName){
        ProviderInfo pf=new ProviderInfo();
        pf.setAddress("127.0.0.1");
        pf.setPort(8081);
        return pf;
    }
}

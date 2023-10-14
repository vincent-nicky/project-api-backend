package com.wsj.apiconsumerapp.manager;

import com.wsj.apiclientsdk.client.MyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyApiClientManager {

    @Autowired
    private MyApiClient myApiClient;

    public String invokeInterface(String interfaceUrl, String interfaceParamsJson) {
        return myApiClient.invokeInterface(interfaceUrl, interfaceParamsJson);
    }

    public String invokeInterface(String accessKey, String secretKey, String interfaceUrl, String interfaceParamsJson) {
        MyApiClient tempClient = new MyApiClient(accessKey, secretKey);
        return tempClient.invokeInterface(accessKey, secretKey, interfaceUrl, interfaceParamsJson);
    }

}

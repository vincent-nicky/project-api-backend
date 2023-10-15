package com.wsj.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.wsj.apiclientsdk.utils.SignUtils.genSign;

/**
 * 调用第三方接口的客户端
 *
 */
public class MyApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";
    private static final String URL = "/api/name/user";

    private String accessKey;

    private String secretKey;

    public MyApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    public String invokeInterface(String interfaceUrl, String interfaceDataJson) {
        return doInvoke(accessKey,secretKey,interfaceUrl,interfaceDataJson);
    }
    public String invokeInterface(String loginUserAk, String loginUserSk, String interfaceUrl, String interfaceDataJson) {
        return doInvoke(loginUserAk,loginUserSk,interfaceUrl,interfaceDataJson);
    }

    private String doInvoke(String accessKey,String secretKey,String interfaceUrl,String interfaceDataJson){
        String newInterfaceDataJson = handleJSON(interfaceDataJson);
        // 开始发送请求
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + interfaceUrl)
                .addHeaders(genHeaders(accessKey, secretKey, interfaceDataJson))
                .body(newInterfaceDataJson)
                .execute();
        System.out.println("【是否成功发送】" + httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println("【响应结果】" + result);
        return result;
    }

    private Map<String, String> genHeaders(String accessKey, String secretKey, String interfaceDataJson) {
        Map<String, String> hashMap = new HashMap<>();
        // 解析JSON字符串并将其转换为Map
        Map<String, Object> jsonMap = new Gson().fromJson(interfaceDataJson, new TypeToken<Map<String, Object>>() {
        }.getType());
        // 提取params和headers Map
        Map<String, Object> headersMap = (Map<String, Object>) jsonMap.get("headers");
        // 遍历
        if (headersMap != null){
            for (String key : headersMap.keySet()) {
                hashMap.put(key, (String) headersMap.get(key));
            }
        }
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = SecureUtil.md5(accessKey + interfaceDataJson);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(encodedBody, secretKey));
        return hashMap;
    }

    private String handleJSON(String interfaceDataJson){
        Gson gson = new Gson();
        Map<String, Object> jsonMap = gson.fromJson(interfaceDataJson, new TypeToken<Map<String, Object>>() {}.getType());
        Map<String, Object> paramsMap = (Map<String, Object>) jsonMap.get("params");
        return gson.toJson(paramsMap);
    }
}

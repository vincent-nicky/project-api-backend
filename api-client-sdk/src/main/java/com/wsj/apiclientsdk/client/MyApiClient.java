package com.wsj.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.wsj.apiclientsdk.model.InterFaceRequest;

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
    public String invokeInterface(String interfaceUrl, String interfaceParamsJson) {

        // Start 构造 Headers
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = SecureUtil.md5(accessKey + interfaceUrl);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(encodedBody, secretKey));
        // over

        // .body() 应该传所调用接口的参数
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + interfaceUrl)
                .addHeaders(hashMap)
                .body(interfaceParamsJson)
                .execute();

        System.out.println(httpResponse.getStatus());

        String result = httpResponse.body();

        System.out.println(result);

        return result;
    }

    public String invokeInterface(String loginUserAk, String loginUserSk, String interfaceUrl, String interfaceParamsJson) {

        // Start 构造 Headers
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", loginUserAk);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = SecureUtil.md5(loginUserAk + interfaceUrl);
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", genSign(encodedBody, loginUserSk));
        // over

        // .body() 应该传所调用接口的参数
        // 开始发送请求
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + interfaceUrl)
                .addHeaders(hashMap)
                .body(interfaceParamsJson)
                .execute();

        System.out.println(httpResponse.getStatus());

        String result = httpResponse.body();

        System.out.println(result);

        return result;
    }
}

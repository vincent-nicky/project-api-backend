package com.wsj.apigateway.filters;

import com.wsj.apiclientsdk.utils.SignUtils;
import com.wsj.apicommon.model.entity.User;
import com.wsj.apicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Slf4j
public class AuthFilter {

    @DubboReference
    private static InnerUserService innerUserService;

    // 参考：https://blog.csdn.net/yqwang75457/article/details/117815474
    // https://blog.51cto.com/u_16123065/6437261
    // https://juejin.cn/post/6844903807839649806

    public static User authFilter(ServerHttpRequest request){

        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        if (nonce != null && Long.parseLong(nonce) > 10000L) {
            return null;
        }

        // 根据ak获取用户信息
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        if (invokeUser == null) {
            return null;
        }
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return null;
        }

        // 时间和当前时间不能超过 5 分钟
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return null;
        }

        return invokeUser;
    }
}

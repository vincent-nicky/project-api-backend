package com.wsj.apigateway.filters;

import com.wsj.apiclientsdk.utils.SignUtils;
import com.wsj.apicommon.model.entity.User;
import com.wsj.apicommon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthFilter {

    @DubboReference
    private UserService userService;


    /**
     * 判断用户是否可进行接口调用
     *
     * @param request
     * @return
     */
    public User doAuth(ServerHttpRequest request) {

        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");

        // TODO 未理解 nonce 的作用
        if (nonce != null && Long.parseLong(nonce) > 10000L) {
            return null;
        }

        // 根据ak获取用户信息
        User invokeUser = userService.getInvokeUser(accessKey);
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

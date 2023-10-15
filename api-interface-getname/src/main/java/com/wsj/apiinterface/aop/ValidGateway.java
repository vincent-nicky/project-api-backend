package com.wsj.apiinterface.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * 验证请求是否通过了网关
 */
@Aspect
@Component
@Slf4j
public class ValidGateway {

    @Around("execution(* com.wsj.apiinterface.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 获取 session
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();

        log.info("【ValidGateway userRole】" + httpServletRequest.getHeader("userRole"));

        // 先判断是否通过网关
        String isPass = httpServletRequest.getHeader("X-ValidGateway-Header");
        if (isPass == null || !isPass.equals("wsjdegateway")) {
            throw new Exception("该请求未通过网关");
        }
        // 执行原方法
        return point.proceed();
    }
}

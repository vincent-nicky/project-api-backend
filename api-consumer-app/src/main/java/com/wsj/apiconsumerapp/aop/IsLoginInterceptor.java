package com.wsj.apiconsumerapp.aop;

import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apicommon.model.entity.User;
import com.wsj.apicommon.service.UserService;
import com.wsj.apiconsumerapp.manager.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.wsj.apicommon.constant.UserConstant.USER_LOGIN_STATE;

@Aspect
@Component
@Slf4j
public class IsLoginInterceptor {

    @DubboReference
    private UserService userService;

    @Around("execution(* com.wsj.apiconsumerapp.controller.*.*(..)) &&" +
            "!execution(* com.wsj.apiconsumerapp.controller.UserController.userLogin(..)) &&" +
            "!execution(* com.wsj.apiconsumerapp.controller.UserController.userRegister(..))"
    )
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 获取 session
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 先判断是否已登录
        Object userIdObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User userOnlyId = (User) userIdObj;
        if (userOnlyId == null || userOnlyId.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        User currentUser = userService.getById(userOnlyId.getId());
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 存入Threadlocal
        UserHolder.set(currentUser);
        // 执行原方法
        Object result = point.proceed();
        // 释放空间
        UserHolder.remove();
        return result;
    }
}

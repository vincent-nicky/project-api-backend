package com.wsj.apiconsumerapp.aop;

import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apicommon.model.entity.User;
import com.wsj.apicommon.service.UserService;
import com.wsj.apiconsumerapp.manager.UserHolder;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wsj.apicommon.constant.UserConstant.USER_LOGIN_STATE;

@Component
public class IsLoginInterceptor implements HandlerInterceptor {

    @DubboReference
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 先判断是否已登录
        Object userIdObj = request.getSession().getAttribute(USER_LOGIN_STATE);
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
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 释放空间
        UserHolder.remove();
    }
}

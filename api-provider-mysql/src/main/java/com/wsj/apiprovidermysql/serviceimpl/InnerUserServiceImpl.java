package com.wsj.apiprovidermysql.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apiprovidermysql.mapper.UserMapper;
import com.wsj.apicommon.model.entity.User;
import com.wsj.apicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 内部用户服务实现类
 *
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}

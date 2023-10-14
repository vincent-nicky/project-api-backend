package com.wsj.apiinnerinterface.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;

import com.wsj.apicommon.model.entity.UserInterfaceInfo;
import com.wsj.apicommon.service.InnerUserInterfaceInfoService;
import com.wsj.apiinnerinterface.mapper.UserInterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 内部用户接口信息服务实现类
 *
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Autowired
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    public boolean invokeCount(long interfaceInfoId, long userId) {
        // 判断
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);

//        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return userInterfaceInfoMapper.update(updateWrapper);
    }
}

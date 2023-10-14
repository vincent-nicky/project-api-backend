package com.wsj.apiprovidermysql.serviceimpl;

import com.wsj.apicommon.service.InnerUserInterfaceInfoService;
import com.wsj.apicommon.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 内部用户接口信息服务实现类
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}

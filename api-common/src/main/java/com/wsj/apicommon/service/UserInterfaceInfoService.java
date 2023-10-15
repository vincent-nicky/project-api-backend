package com.wsj.apicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsj.apicommon.model.entity.UserInterfaceInfo;

import java.util.List;


/**
 * 用户接口信息服务
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    List<UserInterfaceInfo>  listTopInvokeInterfaceInfo(int limit);

    boolean ValidCount(long interfaceId, long userId);
}

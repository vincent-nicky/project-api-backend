package com.wsj.apicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wsj.apicommon.model.entity.InterfaceInfo;


/**
 * 接口信息服务
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    InterfaceInfo getInterfaceInfo(String url, String method);
}

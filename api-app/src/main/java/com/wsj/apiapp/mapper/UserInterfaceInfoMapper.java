package com.wsj.apiapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wsj.apicommon.model.entity.UserInterfaceInfo;


import java.util.List;

/**
 * 用户接口信息 Mapper
 *
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}





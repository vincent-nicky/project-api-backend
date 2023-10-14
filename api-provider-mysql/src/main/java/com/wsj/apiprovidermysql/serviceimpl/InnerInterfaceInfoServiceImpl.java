package com.wsj.apiprovidermysql.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apicommon.model.entity.InterfaceInfo;
import com.wsj.apicommon.service.InnerInterfaceInfoService;
import com.wsj.apiprovidermysql.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 内部接口服务实现类
 *
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }

}

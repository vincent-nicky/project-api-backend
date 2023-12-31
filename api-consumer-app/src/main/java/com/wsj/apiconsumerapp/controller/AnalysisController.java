package com.wsj.apiconsumerapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsj.apicommon.common.BaseResponse;
import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.common.ResultUtils;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apicommon.model.entity.InterfaceInfo;
import com.wsj.apicommon.model.entity.UserInterfaceInfo;
import com.wsj.apicommon.service.InterfaceInfoService;
import com.wsj.apicommon.service.UserInterfaceInfoService;
import com.wsj.apiconsumerapp.annotation.AuthCheck;
import com.wsj.apicommon.model.vo.InterfaceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @DubboReference
    private UserInterfaceInfoService userInterfaceInfoService;

    @DubboReference
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.listTopInvokeInterfaceInfo(3);

        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());

        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(interfaceInfoVOList);
    }
}

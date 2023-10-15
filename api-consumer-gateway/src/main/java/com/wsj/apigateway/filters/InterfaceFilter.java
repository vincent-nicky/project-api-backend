package com.wsj.apigateway.filters;

import com.wsj.apicommon.common.ErrorCode;
import com.wsj.apicommon.exception.BusinessException;
import com.wsj.apicommon.model.entity.InterfaceInfo;
import com.wsj.apicommon.service.InterfaceInfoService;
import com.wsj.apicommon.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InterfaceFilter {

    @DubboReference
    private InterfaceInfoService InterfaceInfoService;

    @DubboReference
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 校验接口是否存在
     * @param path
     * @param method
     * @return
     */
    public InterfaceInfo isValid(String path, String method){
        // 校验接口是否存在
        return InterfaceInfoService.getInterfaceInfo(path, method);
    }

    /**
     * 验证是否还有调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    public boolean validInvokeCount(long interfaceInfoId, long userId){
        // 校验接口次数是否可用
        boolean res = userInterfaceInfoService.ValidCount(interfaceInfoId, userId);
        if(!res){
            throw new BusinessException(ErrorCode.NO_INVOKE_COUNT);
        }
        return true;
    }

    /**
     * 调整调用次数
     * @param interfaceInfoId
     * @param userId
     */
    public void doInvokeCount(long interfaceInfoId, long userId){
        userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }


}

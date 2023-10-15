package com.wsj.apicommon.model.vo;

import com.wsj.apicommon.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class InterfacePageVO implements Serializable {
    private long current;
    private long size;
    private String sortField;
    private String sortOrder;
    private String description;
    private InterfaceInfo interfaceInfoQuery;

    private static final long serialVersionUID = 1L;
}

package com.ntschy.crop.entity.base;

import lombok.Data;

@Data
public class OperationLog {
    private String id;

    private String operateType;

    private String operateDesc;

    private String operateMethod;

    private String operateRequParam;

    private String operateRespParam;

    private String operateUser;

    private String requestURI;

    private String operateTime;

    private String operateIp;
}

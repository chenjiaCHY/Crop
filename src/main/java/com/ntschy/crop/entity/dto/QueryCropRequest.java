package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryCropRequest {

    // 村
    private String village;

    // 户
    private String household;

    // 品种
    private String breed;

    // 类型
    private String season;

    // 是否流转
    private String isTransfer;

    @NotNull(message = "currPage不能为空")
    private Integer currPage;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
}

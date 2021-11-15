package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExportCropRequest {
    // 村
    private String village;
    // 户
    private String household;
    // 品种
    private String breed;
    // 季节
    private String season;
    // 是否流转
    private String isTransfer;
    // 来源，MANAGE：管理页面；HOME：一张图页面
    @NotBlank(message = "origin不能为空")
    private String origin;
}

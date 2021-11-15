package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VillageStatisticsRequest {

    @NotBlank(message = "season不能为空")
    private String season;

    @NotNull(message = "currPage不能为空")
    private Integer currPage;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
}

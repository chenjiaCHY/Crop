package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FeedbackRequest {
    private String status;

    @NotNull(message = "currPage不能为空")
    private Integer currPage;

    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;
}

package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddUserRequest {

    private String userId;

    @NotBlank(message = "account不能为空")
    private String account;

    @NotBlank(message = "name不能为空")
    private String name;

    private String password;

    @NotBlank(message = "phone不能为空")
    private String phone;

    @NotBlank(message = "village不能为空")
    private String village;

    @NotBlank(message = "community不能为空")
    private String community;

    @NotBlank(message = "roleId不能为空")
    private String roleId;

    private String createTime;
}

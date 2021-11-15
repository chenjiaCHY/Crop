package com.ntschy.crop.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UpdateRoleRequest {

    @NotBlank(message = "roleId不能为空")
    private String roleId;

    @NotBlank(message = "roleName不能为空")
    private String roleName;

    private List<Integer> actionList;
}

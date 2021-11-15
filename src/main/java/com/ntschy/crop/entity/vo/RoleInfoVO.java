package com.ntschy.crop.entity.vo;

import com.ntschy.crop.entity.Action;
import lombok.Data;

import java.util.List;

@Data
public class RoleInfoVO {

    private Integer rowNumber;

    private String roleId;

    private String roleName;

    private String createTime;

    private List<Action> actionList;
}

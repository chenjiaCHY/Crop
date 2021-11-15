/*
 * Copyright (c) 2021. All Rights Reserved.
 * ProjectName: underground
 * FileName: AuthorityController.java
 * Author: 陈佳
 * Date: 2021/8/19 下午3:30
 * Version: 1.0
 * LastModified
 *
 */

package com.ntschy.crop.controller;

import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.RoleInfoVO;
import com.ntschy.crop.entity.vo.UserInfoVO;
import com.ntschy.crop.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/authority")
@Validated
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    /**
     * 用户登录
     * @param userLogin
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result userLogin(@RequestBody @Validated UserLogin userLogin) {
        try {
            Map<String, Object> map = authorityService.userLogin(userLogin);
            return new Result(map);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 新增角色
     * @param addRoleRequest
     * @return
     */
    @PostMapping("/addRole")
    @ResponseBody
    public Result addRole(@RequestBody @Validated AddRoleRequest addRoleRequest) {
        try {
            Result result = authorityService.addRole(addRoleRequest);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 修改角色
     * @param updateRoleRequest
     * @return
     */
    @PostMapping("/updateRole")
    @ResponseBody
    public Result updateRole(@RequestBody @Validated UpdateRoleRequest updateRoleRequest) {
        try {
            Result result = authorityService.updateRole(updateRoleRequest);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @GetMapping("/deleteRole")
    @ResponseBody
    public Result deleteRole(@RequestParam("roleId") String roleId) {
        try {
            Result result = authorityService.deleteRole(roleId);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 新增用户
     * @param addUserRequest
     * @return
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody @Validated AddUserRequest addUserRequest) {
        try {
            Result result = authorityService.addUser(addUserRequest);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 修改用户
     * @param updateUserRequest
     * @return
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public Result updateUser(@RequestBody @Validated UpdateUserRequest updateUserRequest) {
        try {
            Result result = authorityService.updateUser(updateUserRequest);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestParam("userId") String userId) {
        try {
            Result result = authorityService.deleteUser(userId);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取所有权限列表，新增角色时选择权限用
     * @return
     */
    @GetMapping("/getActionList")
    @ResponseBody
    public Result getActionList() {
        try {
            Result result = authorityService.getActionList();
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取角色列表，分页
     * @return
     */
    @PostMapping("/getRoleList")
    @ResponseBody
    public Result getRoleList(@RequestBody @Validated QueryRoleRequest queryRoleRequest) {
        try {
            PageQuery pageQuery = authorityService.getRoleList(queryRoleRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取用户列表，分页
     * @return
     */
    @PostMapping("/getUserList")
    @ResponseBody
    public Result getUserList(@RequestBody @Validated QueryUserRequest queryUserRequest) {
        try {
            PageQuery pageQuery = authorityService.getUserList(queryUserRequest);
            return new Result<>(pageQuery);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取所有角色，新增用户时选择角色用
     * @return
     */
    @GetMapping("/getFullRoleList")
    @ResponseBody
    public Result getFullRoleList() {
        try {
            Result result = authorityService.getFullRoleList();
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取角色详情
     * @return
     */
    @GetMapping("/getRoleInfo")
    @ResponseBody
    public Result getRoleInfo(@RequestParam("roleId") String roleId) {
        try {
            RoleInfoVO roleInfoVO = authorityService.getRoleInfo(roleId);
            return new Result<>(roleInfoVO);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 获取用户详情
     * @return
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfo(@RequestParam("userId") String userId) {
        try {
            UserInfoVO userInfoVO = authorityService.getUserInfo(userId);
            return new Result<>(userInfoVO);
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    /**
     * 修改密码
     * @return
     */
    @PostMapping("/modifyUserPwd")
    @ResponseBody
    public Result modifyUserPwd(@RequestBody @Validated ModifyPwdRequest modifyPwdRequest) {
        try {
            Result result = authorityService.modifyUserPwd(modifyPwdRequest);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }

    @GetMapping("/resetPwd")
    @ResponseBody
    public Result resetPwd(@RequestParam("account") String account) {
        try {
            Result result = authorityService.resetPwd(account);
            return result;
        } catch (Exception e) {
            return new Result(false, e.getMessage());
        }
    }
}

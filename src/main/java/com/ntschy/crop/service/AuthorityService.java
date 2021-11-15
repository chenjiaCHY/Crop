package com.ntschy.crop.service;

import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.LoginToken;
import com.ntschy.crop.entity.vo.RoleInfoVO;
import com.ntschy.crop.entity.vo.UserInfoVO;

import java.util.Map;

public interface AuthorityService {

    Map<String, Object> userLogin(UserLogin userLogin);

    Result addRole(AddRoleRequest addRoleRequest);

    Result updateRole(UpdateRoleRequest updateRoleRequest);

    Result deleteRole(String roleId);

    Result addUser(AddUserRequest addUserRequest);

    Result updateUser(UpdateUserRequest updateUserRequest);

    Result deleteUser(String userId);

    Result getActionList();

    Result getFullRoleList();

    PageQuery getRoleList(QueryRoleRequest queryRoleRequest);

    PageQuery getUserList(QueryUserRequest queryUserRequest);

    RoleInfoVO getRoleInfo(String roleId);

    UserInfoVO getUserInfo(String userId);

    LoginToken getLoginToken(String userID, String getCurrentDateTime);

    void updateLoginTokenExpiresTime(String token, String convertDateToString);

    Result modifyUserPwd(ModifyPwdRequest modifyPwdRequest);

    Result resetPwd(String userId);
}

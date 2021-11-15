package com.ntschy.crop.dao;

import com.ntschy.crop.entity.Action;
import com.ntschy.crop.entity.RoleActionMapping;
import com.ntschy.crop.entity.dto.AddUserRequest;
import com.ntschy.crop.entity.dto.UpdateUserRequest;
import com.ntschy.crop.entity.vo.LoginToken;
import com.ntschy.crop.entity.vo.RoleInfoVO;
import com.ntschy.crop.entity.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityDao {

    RoleInfoVO getRoleInfo(@Param("roleId") String roleID) throws RuntimeException;

    UserInfoVO getUserInfo(@Param("userId") String userId,
                              @Param("account") String account,
                              @Param("pwd") String pwd) throws RuntimeException;

    void insertLoginToken(LoginToken loginToken) throws RuntimeException;

    Integer getRoleCountById(@Param("roleId") String roleId) throws RuntimeException;

    Integer getRoleCountByName(@Param("roleId") String roleId, @Param("roleName") String roleName) throws RuntimeException;

    void deleteRoleActionMapping(@Param("roleId") String roleId) throws RuntimeException;

    void deleteRole(@Param("roleId") String roleId) throws RuntimeException;

    void insertRoleActionMapping(@Param("mappings") List<RoleActionMapping> mappings) throws RuntimeException;

    void insertRole(RoleInfoVO roleInfoVO) throws RuntimeException;

    void updateRole(RoleInfoVO roleInfoVO) throws RuntimeException;

    Integer getUserCountByAccount(@Param("userId") String userId, @Param("account") String account) throws RuntimeException;

    void insertUser(AddUserRequest addUserRequest) throws RuntimeException;

    void updateUser(UpdateUserRequest updateUserRequest) throws RuntimeException;

    void deleteUser(@Param("userId") String userId) throws RuntimeException;

    List<Action> getActionList() throws RuntimeException;

    List<RoleInfoVO> getFullRoleList() throws RuntimeException;

    Integer getRoleCount(String roleName) throws RuntimeException;

    List<RoleInfoVO> getRoleList(@Param("roleName") String roleName,
                                 @Param("startNo") Integer startNo,
                                 @Param("endNo") Integer endNo) throws RuntimeException;

    Integer getRoleInUseCount(@Param("roleId") String roleId) throws RuntimeException;

    Integer getUserCount(@Param("name") String name) throws RuntimeException;

    List<UserInfoVO> getUserList(@Param("name") String name,
                                 @Param("startNo") Integer startNo,
                                 @Param("endNo") Integer endNo) throws RuntimeException;

    void modifyUserPwd(@Param("account") String account,
                       @Param("newPwd") String newPwd) throws RuntimeException;

    LoginToken getLoginToken(@Param("userId") String userId, @Param("expiresTime") String expiresTime) throws RuntimeException;

    void updateLoginTokenExpiresTime(@Param("token") String token, @Param("expiresTime") String expiresTime) throws RuntimeException;

    String getPasswordByAccount(@Param("account") String account) throws RuntimeException;
}

package com.ntschy.crop.service.impl;

import com.ntschy.crop.dao.AuthorityDao;
import com.ntschy.crop.entity.Action;
import com.ntschy.crop.entity.RoleActionMapping;
import com.ntschy.crop.entity.base.PageQuery;
import com.ntschy.crop.entity.base.Result;
import com.ntschy.crop.entity.dto.*;
import com.ntschy.crop.entity.vo.LoginToken;
import com.ntschy.crop.entity.vo.RoleInfoVO;
import com.ntschy.crop.entity.vo.UserInfoVO;
import com.ntschy.crop.service.AuthorityService;
import com.ntschy.crop.utils.JwtUtil;
import com.ntschy.crop.utils.MD5Utils;
import com.ntschy.crop.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityDao authorityDao;

    /**
     * 用户登录
     * @param userLogin
     * @return
     */
    @Override
    public Map<String, Object> userLogin(UserLogin userLogin) {

        UserInfoVO userInfoVO = authorityDao.getUserInfo(null, userLogin.getAccount(), MD5Utils.StringToMD5(userLogin.getPwd()));

        if (ObjectUtils.isEmpty(userInfoVO)) {
            throw new RuntimeException("账号或密码错误！！！");
        }

        String roleId = Optional.ofNullable(userInfoVO.getRoleId()).orElse("");
        RoleInfoVO roleInfoVO = null;
        if (StringUtils.isNotBlank(roleId)) {
            roleInfoVO = authorityDao.getRoleInfo(roleId);
        }

        String token = JwtUtil.sign(userInfoVO.getAccount(), userInfoVO.getUserId());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, JwtUtil.EXPIRE_HOUR);
        LoginToken loginToken = new LoginToken(userInfoVO.getUserId(), token, Utils.ConvertDateToString(calendar, Utils.YYYYMMDDHH24MMSS), 1, Utils.GetCurrentDateTime());
        loginToken.setTokenId(Utils.GenerateUUID(32));
        authorityDao.insertLoginToken(loginToken);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", userInfoVO.getUserId());
        map.put("userName", userInfoVO.getName());
        map.put("role", roleInfoVO);

        return map;
    }

    /**
     * 新增角色
     * @param addRoleRequest
     * @return
     */
    @Override
    public Result addRole(AddRoleRequest addRoleRequest) {

        String roleId = Utils.GenerateUUID(32);
        String roleName = addRoleRequest.getRoleName();

        Integer count = authorityDao.getRoleCountByName(roleId, roleName);
        if (count > 0) {
            throw new RuntimeException("该角色已存在，请勿重新创建！！！");
        }

        List<Integer> actionList = Optional.ofNullable(addRoleRequest.getActionList()).orElse(Collections.emptyList());
        List<RoleActionMapping> roleActionMappings = new ArrayList<>();

        if (!CollectionUtils.isEmpty(actionList)) {
            for (Integer actionId : actionList) {
                RoleActionMapping roleActionMapping = new RoleActionMapping();
                roleActionMapping.setRoleId(roleId);
                roleActionMapping.setActionId(actionId);
                roleActionMappings.add(roleActionMapping);
            }
        }

        if (!CollectionUtils.isEmpty(roleActionMappings)) {
            authorityDao.insertRoleActionMapping(roleActionMappings);
        }

        RoleInfoVO roleInfoVO = new RoleInfoVO();
        roleInfoVO.setRoleId(roleId);
        roleInfoVO.setRoleName(roleName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        roleInfoVO.setCreateTime(sdf.format(new Date()));

        authorityDao.insertRole(roleInfoVO);

        return new Result(true, "新增角色成功");
    }

    /**
     * 修改角色
     * @param updateRoleRequest
     * @return
     */
    @Override
    public Result updateRole(UpdateRoleRequest updateRoleRequest) {

        String roleId = updateRoleRequest.getRoleId();
        String roleName = updateRoleRequest.getRoleName();
        List<Integer> actionList = Optional.ofNullable(updateRoleRequest.getActionList()).orElse(Collections.emptyList());

        // 检查角色id是否存在
        Integer roleIdCount = authorityDao.getRoleCountById(roleId);
        if (roleIdCount == 0) {
            throw new RuntimeException("角色id不存在");
        }
        // 检查角色名称是否存在
        Integer roleNameCount = authorityDao.getRoleCountByName(roleId, roleName);
        if (roleNameCount > 0) {
            throw new RuntimeException("角色名称已存在，请勿重新创建！！！");
        }

        authorityDao.deleteRoleActionMapping(roleId);

        List<RoleActionMapping> roleActionMappings = new ArrayList<>();

        if (!CollectionUtils.isEmpty(actionList)) {
            for (Integer actionId : actionList) {
                RoleActionMapping roleActionMapping = new RoleActionMapping();
                roleActionMapping.setRoleId(roleId);
                roleActionMapping.setActionId(actionId);
                roleActionMappings.add(roleActionMapping);
            }
        }

        if (!CollectionUtils.isEmpty(roleActionMappings)) {
            authorityDao.insertRoleActionMapping(roleActionMappings);
        }

        RoleInfoVO roleInfoVO = new RoleInfoVO();
        roleInfoVO.setRoleId(roleId);
        roleInfoVO.setRoleName(roleName);
        authorityDao.updateRole(roleInfoVO);

        return new Result(true, "更新角色成功");
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Override
    public Result deleteRole(String roleId) {

        if (StringUtils.isBlank(roleId)) {
            return new Result(false, "roleId为空");
        }
        // 检查角色id是否存在
        Integer roleIdCount = authorityDao.getRoleCountById(roleId);
        if (roleIdCount == 0) {
            throw new RuntimeException("角色id不存在");
        }
        // 检查是否有用户配了该角色
        Integer roleInUseCount = authorityDao.getRoleInUseCount(roleId);
        if (roleInUseCount > 0) {
            throw new RuntimeException("该角色在使用中，不能删除");
        }

        authorityDao.deleteRoleActionMapping(roleId);
        authorityDao.deleteRole(roleId);

        return new Result(true, "删除角色成功");
    }

    /**
     * 新增用户
     * @param addUserRequest
     * @return
     */
    @Override
    public Result addUser(AddUserRequest addUserRequest) {

        String userId = Utils.GenerateUUID(32);
        String account = addUserRequest.getAccount();
        String password = "111111";

        Integer count = authorityDao.getUserCountByAccount(userId, account);
        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        addUserRequest.setUserId(userId);
        addUserRequest.setPassword(MD5Utils.StringToMD5(password));
        addUserRequest.setCreateTime(sdf.format(new Date()));

        authorityDao.insertUser(addUserRequest);

        return new Result(true, "新增用户成功");
    }

    /**
     * 修改用户
     * @param updateUserRequest
     * @return
     */
    @Override
    public Result updateUser(UpdateUserRequest updateUserRequest) {
        String userId = updateUserRequest.getUserId();
        String account = updateUserRequest.getAccount();

        Integer count = authorityDao.getUserCountByAccount(userId, account);
        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        authorityDao.updateUser(updateUserRequest);
        return new Result(true, "修改用户成功");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Override
    public Result deleteUser(String userId) {

        authorityDao.deleteUser(userId);
        return new Result(true, "删除用户成功");
    }

    /**
     * 获取所有权限列表
     * @return
     */
    @Override
    public Result getActionList() {

        List<Action> actionList = authorityDao.getActionList();

        actionList = Optional.ofNullable(actionList).orElse(Collections.emptyList());

        return new Result<>(actionList);
    }

    /**
     * 获取所有角色列表
     * @return
     */
    @Override
    public Result getFullRoleList() {

        List<RoleInfoVO> roleList = authorityDao.getFullRoleList();

        roleList = Optional.ofNullable(roleList).orElse(Collections.emptyList());

        return new Result<>(roleList);
    }

    /**
     * 获取角色列表
     * @param queryRoleRequest
     * @return
     */
    @Override
    public PageQuery getRoleList(QueryRoleRequest queryRoleRequest) {
        String roleName = queryRoleRequest.getName();

        Integer startNo = PageQuery.startLine(queryRoleRequest.getCurrPage(), queryRoleRequest.getPageSize());
        Integer endNo = PageQuery.endLine(queryRoleRequest.getCurrPage(), queryRoleRequest.getPageSize());

        Integer total = authorityDao.getRoleCount(roleName);

        List<RoleInfoVO> roleInfoVOList = authorityDao.getRoleList(roleName, startNo, endNo);

        int rowNumber = (queryRoleRequest.getCurrPage() - 1) * queryRoleRequest.getPageSize() + 1;

        if (!CollectionUtils.isEmpty(roleInfoVOList)) {
            for (RoleInfoVO roleInfo : roleInfoVOList) {
                roleInfo.setRowNumber(rowNumber);
                rowNumber ++;
            }
        }

        PageQuery pageQuery = new PageQuery(queryRoleRequest.getCurrPage(), queryRoleRequest.getPageSize(),
                total, Optional.ofNullable(roleInfoVOList).orElse(Collections.emptyList()));

        return pageQuery;
    }

    /**
     * 获取用户列表
     * @param queryUserRequest
     * @return
     */
    @Override
    public PageQuery getUserList(QueryUserRequest queryUserRequest) {
        Integer startNo = PageQuery.startLine(queryUserRequest.getCurrPage(), queryUserRequest.getPageSize());
        Integer endNo = PageQuery.endLine(queryUserRequest.getCurrPage(), queryUserRequest.getPageSize());

        Integer total = authorityDao.getUserCount(queryUserRequest.getName());

        List<UserInfoVO> userInfoList = authorityDao.getUserList(queryUserRequest.getName(), startNo, endNo);

        int rowNumber = (queryUserRequest.getCurrPage() - 1) * queryUserRequest.getPageSize() + 1;

        if (!CollectionUtils.isEmpty(userInfoList)) {
            for (UserInfoVO userInfo : userInfoList) {
                userInfo.setRowNumber(rowNumber);
                rowNumber ++;
            }
        }

        PageQuery pageQuery = new PageQuery(queryUserRequest.getCurrPage(), queryUserRequest.getPageSize(),
                total, Optional.ofNullable(userInfoList).orElse(Collections.emptyList()));

        return pageQuery;
    }

    @Override
    public RoleInfoVO getRoleInfo(String roleId) {

        RoleInfoVO roleInfoVO = authorityDao.getRoleInfo(roleId);

        return roleInfoVO;

    }

    @Override
    public UserInfoVO getUserInfo(String userId) {

        UserInfoVO userInfoVO = authorityDao.getUserInfo(userId, null, null);

        return userInfoVO;
    }

    @Override
    public LoginToken getLoginToken(String userID, String getCurrentDateTime) {
        return authorityDao.getLoginToken(userID, getCurrentDateTime);
    }

    @Override
    public void updateLoginTokenExpiresTime(String token, String convertDateToString) {
        authorityDao.updateLoginTokenExpiresTime(token, convertDateToString);
    }

    /**
     * 修改密码
     * @param modifyPwdRequest
     * @return
     */
    @Override
    public Result modifyUserPwd(ModifyPwdRequest modifyPwdRequest) {

        // 先检查旧密码是否有效
        String oldPasswd = authorityDao.getPasswordByAccount(modifyPwdRequest.getAccount());
        if (StringUtils.isBlank(oldPasswd) || !oldPasswd.equals(MD5Utils.StringToMD5(modifyPwdRequest.getOldPwd()))) {
            return new Result(false, "旧密码错误!!!");
        }

        modifyPwdRequest.setNewPwd(MD5Utils.StringToMD5(modifyPwdRequest.getNewPwd()));
        authorityDao.modifyUserPwd(modifyPwdRequest.getAccount(), modifyPwdRequest.getNewPwd());

        return new Result(true, "更新密码成功！");

    }

    /**
     * 重置密码
     * @param account
     * @return
     */
    @Override
    public Result resetPwd(String account) {

        if (StringUtils.isBlank(account)) {
            return new Result(false, "account为空");
        }

        String newPwd = MD5Utils.StringToMD5("111111");
        authorityDao.modifyUserPwd(account, newPwd);

        return new Result(true, "重置密码成功");
    }


}

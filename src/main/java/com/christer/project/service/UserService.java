package com.christer.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.myapicommon.model.dto.user.UserAddParam;
import com.christer.project.model.dto.user.*;
import com.christer.project.model.vo.UserInfoVO;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 15:07
 * Description:
 */
public interface UserService {
    /**
     * 用户注册
     * @param userParam 账号， 密码
     * @return id
     */
    Long registerUser(UserRegisterParam userParam);

    /**
     * 用户登录
     * @param userParam 账号，密码
     * @return token
     */
    UserInfoVO loginUser(UserLoginParam userParam);

    /**
     * 用户分页查询
     * @param userParam
     * @return
     */
    Page<UserInfoVO> queryUserByCondition(UserQueryParam userParam);

    /**
     * 查看详情
     * @param id 用户id
     * @return obj
     */
    UserInfoVO selectById(Long id);

    /**
     * 用户信息更新
     * @param userUpdateParam
     * @return
     */
    boolean updateUserInfo(UserUpdateParam userUpdateParam);

    /**
     * 修改密码
     * @param changePasswordParam 请求参数
     * @return flag
     */
    Boolean changePassword(ChangePasswordParam changePasswordParam);

    /**
     * 新增用户
     * @param userAddParam
     * @return
     */
    Boolean addUser(UserAddParam userAddParam);
}

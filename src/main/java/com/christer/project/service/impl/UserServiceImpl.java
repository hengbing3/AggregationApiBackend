package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.UserMapper;
import com.christer.project.model.dto.user.UserLoginParam;
import com.christer.project.model.dto.user.UserQueryParam;
import com.christer.project.model.dto.user.UserRegisterParam;
import com.christer.project.model.dto.user.UserUpdateParam;
import com.christer.project.model.entity.UserEntity;
import com.christer.project.model.vo.UserInfoVO;
import com.christer.project.service.UserService;
import com.christer.project.util.BeanCopyUtil;
import com.christer.project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

import static com.christer.project.constant.CommonConstant.SALT;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 15:48
 * Description:
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Long registerUser(UserRegisterParam userParam) {
        if (!StringUtils.equals(userParam.getUserPassword(),userParam.getCheckPassword())) {
            throw new BusinessException("两次密码输入不一致！");
        }
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.lambda().eq(UserEntity::getDeletedFlag, "false")
                .eq(UserEntity::getUserAccount, userParam.getUserAccount());
        final UserEntity existUserEntity = userMapper.selectOne(userEntityQueryWrapper);

        ThrowUtils.throwIf(null != existUserEntity, "账号已经存在！");

        UserEntity userEntity = BeanUtil.copyProperties(userParam, UserEntity.class);
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userEntity.getUserPassword()).getBytes());
        userEntity.setUserPassword(encryptPassword);
        userMapper.insert(userEntity);
        return userEntity.getId();
    }

    @Override
    public UserInfoVO loginUser(UserLoginParam userParam) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().
                eq(UserEntity::getUserAccount,userParam.getUserAccount());
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(userEntity == null, "用户不存在！");
        // 校验用户密码输入是否正确
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userParam.getUserPassword()).getBytes());
        ThrowUtils.throwIf(!StringUtils.equals(encryptPassword, Objects.requireNonNull(userEntity).getUserPassword())
        ,ResultCode.PARAMS_ERROR, "用户名或密码错误！");
        return BeanUtil.copyProperties(userEntity, UserInfoVO.class);
    }

    @Override
    public Page<UserInfoVO> queryUserByCondition(UserQueryParam userParam) {
        //分页参数
        final Page<UserEntity> rowPage = new Page<>(userParam.getCurrentPage(), userParam.getPageSize());
        final LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 添加用户查询条件
        queryWrapper.like(org.springframework.util.StringUtils.hasLength(userParam.getUserName()), UserEntity::getUserName, userParam.getUserName());
        Page<UserEntity> userEntityPage = userMapper.selectPage(rowPage, queryWrapper);
        List<UserInfoVO> userInfoVOS = BeanCopyUtil.copyListProperties(userEntityPage.getRecords(), UserInfoVO::new);
        Page<UserInfoVO> userInfoPage = new Page<>(userEntityPage.getCurrent(), userEntityPage.getSize(), userEntityPage.getTotal());
        userInfoPage.setRecords(userInfoVOS);
        return userInfoPage;
    }

    @Override
    public UserInfoVO selectById(Long id) {
        Assert.notNull(id," id 不能为空！");
        return BeanUtil.copyProperties(userMapper.selectById(id), UserInfoVO.class);
    }

    @Override
    public boolean updateUserInfo(UserUpdateParam userUpdateParam) {
        // 业务层校验
        ValidateUtil.validateBean(userUpdateParam);
        // 更新用户信息
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<UserEntity> eq = userEntityUpdateWrapper.lambda()
                .set(UserEntity::getUserName, userUpdateParam.getUserName())
                .set(UserEntity::getUserAvatar, userUpdateParam.getUserAvatar())
                .set(UserEntity::getUserProfile, userUpdateParam.getUserProfile())
                .eq(UserEntity::getId, userUpdateParam.getId())
                .eq(UserEntity::getDeletedFlag, '0');
        return userMapper.update(null, eq) > 0;
    }


}

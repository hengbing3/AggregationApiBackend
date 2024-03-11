package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.christer.myapicommon.model.entity.UserEntity;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.DepartmentMapper;
import com.christer.project.mapper.UserMapper;
import com.christer.project.model.dto.user.*;
import com.christer.project.model.vo.UserInfoVO;
import com.christer.project.service.UserService;
import com.christer.project.util.BeanCopyUtil;
import com.christer.project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.christer.project.constant.CommonConstant.*;
import static com.christer.project.util.EncryptUtil.generateRsaEncryptData;
import static com.christer.project.util.EncryptUtil.getDigestSign;

/**
 * @author Christer
 * @version 1.0
 * @date 2023-12-02 15:48
 * Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final DepartmentMapper departmentMapper;
    @Value("${default-flowable-ui-service}")
    private String flowableServiceUI;

    private final ResourceLoader resourceLoader;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerUser(UserRegisterParam userParam) {
        if (!StringUtils.equals(userParam.getUserPassword(), userParam.getCheckPassword())) {
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
        // 设置默认用户信息
        userEntity.setUserRole("user");
        userEntity.setUserAvatar(DEFAULT_AVATAR);
        // 注册时，分配密钥
        userEntity.setAccessKey(DigestUtil.md5Hex(SALT + userParam.getUserAccount() + RandomUtil.randomNumbers(5)));
        userEntity.setSecretKey(DigestUtil.md5Hex(SALT + userParam.getUserAccount() + RandomUtil.randomNumbers(8)));
        userMapper.insert(userEntity);
        // 新增用户部门关联关系
        departmentMapper.insertUserAndDepartmentRelation(userEntity.getId(), DEFAULT_DEPARTMENT);
        userEntity.setUserPassword(userParam.getUserPassword());
        userEntity.setAccessKey(null);
        userEntity.setSecretKey(null);
        userEntity.setUserAvatar(null);
        extractedSyncUserInfo(userEntity);
        return userEntity.getId();
    }

    private void extractedSyncUserInfo(UserEntity userEntity) {
        // 新增用户后也往flowable 里插入用户信息
        final HashMap<String, String> param = new HashMap<>();
        final String jsonStr = JSONUtil.toJsonStr(userEntity);
        final String encrypt = generateRsaEncryptData(jsonStr);
        // 生成签名，防止数据被篡改
        final String digestHex = getDigestSign(userEntity.getUserAccount(), SALT);
        param.put("sign", digestHex);
        final HttpResponse execute = HttpRequest.post(flowableServiceUI + "/addUser")
                .body(encrypt, "application/json")
                .addHeaders(param)
                .charset(StandardCharsets.UTF_8)
                .execute();
        if (execute.getStatus() != 200) {
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "注册失败！");
        }
    }


    @Override
    public UserInfoVO loginUser(UserLoginParam userParam) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().
                eq(UserEntity::getUserAccount, userParam.getUserAccount());
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(userEntity == null, "用户不存在！");
        // 校验用户密码输入是否正确
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userParam.getUserPassword()).getBytes());
        ThrowUtils.throwIf(!StringUtils.equals(encryptPassword, Objects.requireNonNull(userEntity).getUserPassword())
                , ResultCode.PARAMS_ERROR, "用户名或密码错误！");
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
        Assert.notNull(id, " id 不能为空！");
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePassword(final ChangePasswordParam changePasswordParam) {
        if (!StringUtils.equals(changePasswordParam.getUserPassword(), changePasswordParam.getConfirmPassword())) {
            throw new BusinessException("输入的密码不一致！");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + changePasswordParam.getUserPassword()).getBytes());
        final UserEntity userEntity = userMapper.selectById(changePasswordParam.getId());
        if (StringUtils.equals(encryptPassword, userEntity.getUserPassword())) {
            throw new BusinessException("新密码不能与旧密码一致! ");
        }
        final LambdaUpdateWrapper<UserEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserEntity::getUserPassword, encryptPassword)
                        .eq(UserEntity::getId, changePasswordParam.getId());
        final int update = userMapper.update(null, updateWrapper);
        userEntity.setUserPassword(changePasswordParam.getUserPassword());
        final String jsonStr = JSONUtil.toJsonStr(userEntity);
        // 同步工作流的用户信息
        final HttpResponse response = HttpRequest.put(flowableServiceUI + "/changeUserPassword")
                .body(jsonStr, "application/json")
                .execute();
        if (response.getStatus() != 200) {
            throw new BusinessException("密码修改失败！");
        }
        return update > 0;
    }

    /**
     * 读取公钥资源
     *
     * @return
     */
    public String readPublicKey() {
        final Resource resource = resourceLoader.getResource("classpath:static/publicKey.txt");
        try (InputStream inputStream = resource.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuilder publicKey = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                publicKey.append(line);
            }
            return publicKey.toString();

        } catch (IOException e) {
            log.error("公钥文件读取失败:{}", e.getMessage());
            throw new BusinessException(ResultCode.FAILED, "操作失败！");
        }
    }

}

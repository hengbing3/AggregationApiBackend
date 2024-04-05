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
import com.christer.myapicommon.model.dto.user.UserAddParam;
import com.christer.myapicommon.model.entity.UserEntity;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.DepartmentMapper;
import com.christer.project.mapper.UserMapper;
import com.christer.project.model.dto.user.*;
import com.christer.project.model.vo.UserInfoVO;
import com.christer.project.service.FileService;
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
import java.nio.file.FileSystems;
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

    private final FileService fileService;


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
        final UserInfoVO userInfoVO = BeanUtil.copyProperties(userEntity, UserInfoVO.class);
        // 获取用户关联的部门信息
        userInfoVO.setDepartmentId(departmentMapper.selectDepartmentIdByUserId(userEntity.getId()));
        return userInfoVO;
    }

    @Override
    public Page<UserInfoVO> queryUserByCondition(UserQueryParam userParam) {
        //分页参数
        final Page<UserEntity> rowPage = new Page<>(userParam.getCurrentPage(), userParam.getPageSize());
        final LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        // 添加用户查询条件
        queryWrapper.like(org.springframework.util.StringUtils.hasText(userParam.getUserName()), UserEntity::getUserName, userParam.getUserName());
        queryWrapper.like(org.springframework.util.StringUtils.hasText(userParam.getUserAccount()), UserEntity::getUserAccount, userParam.getUserAccount());
        queryWrapper.eq(org.springframework.util.StringUtils.hasText(userParam.getUserRole()), UserEntity::getUserRole, userParam.getUserRole());
        Page<UserEntity> userEntityPage = userMapper.selectPage(rowPage, queryWrapper);
        List<UserInfoVO> userInfoVOS = BeanCopyUtil.copyListProperties(userEntityPage.getRecords(), UserInfoVO::new);
        for (final UserInfoVO userInfoVO : userInfoVOS) {
            if (StringUtils.startsWith(userInfoVO.getUserAvatar(), "store")) {
                userInfoVO.setUserAvatar("http://localhost:8081/attachments" + "/" + userInfoVO.getUserAvatar());
            }
        }
        Page<UserInfoVO> userInfoPage = new Page<>(userEntityPage.getCurrent(), userEntityPage.getSize(), userEntityPage.getTotal());
        userInfoPage.setRecords(userInfoVOS);
        return userInfoPage;
    }

    @Override
    public UserInfoVO selectById(Long id) {
        Assert.notNull(id, " id 不能为空！");
        final UserInfoVO userInfoVO = BeanUtil.copyProperties(userMapper.selectById(id), UserInfoVO.class);
        if (StringUtils.startsWith(userInfoVO.getUserAvatar(), "store")) {
            userInfoVO.setUserAvatar("http://localhost:8081/attachments" + "/" + userInfoVO.getUserAvatar());
        }
        // 获取用户关联的部门信息
        userInfoVO.setDepartmentId(departmentMapper.selectDepartmentIdByUserId(userInfoVO.getId()));
        return userInfoVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserInfo(UserUpdateParam userUpdateParam) {
        // 业务层校验
        ValidateUtil.validateBean(userUpdateParam);
        // 判断头像的路径是否需要转换
        if (StringUtils.startsWith(userUpdateParam.getUserAvatar(), "temp")) {
            userUpdateParam.setUserAvatar(fileService.transferToFinalPath(userUpdateParam.getUserAvatar()));
        }
        // 更新用户信息
        UpdateWrapper<UserEntity> userEntityUpdateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<UserEntity> eq = userEntityUpdateWrapper.lambda()
                .set(StringUtils.isNotBlank(userUpdateParam.getUserName()), UserEntity::getUserName, userUpdateParam.getUserName())
                .set(StringUtils.isNotBlank(userUpdateParam.getUserAvatar()), UserEntity::getUserAvatar, userUpdateParam.getUserAvatar())
                .set(StringUtils.isNotBlank(userUpdateParam.getUserProfile()), UserEntity::getUserProfile, userUpdateParam.getUserProfile())
                .set(StringUtils.isNotBlank(userUpdateParam.getUserRole()), UserEntity::getUserRole, userUpdateParam.getUserRole())
                .eq(UserEntity::getId, userUpdateParam.getId())
                .eq(UserEntity::getDeletedFlag, '0');
        // 获取旧的部门id
        final Long oldDepartmentId = departmentMapper.selectDepartmentIdByUserId(userUpdateParam.getId());
        // flowable 同步更新用户部门信息
        final HashMap<String, String> param = new HashMap<>();
         param.put("userId", String.valueOf(userUpdateParam.getId()));
         param.put("departmentId", String.valueOf(userUpdateParam.getDepartmentId()));
         param.put("oldDepartmentId", String.valueOf(oldDepartmentId));
         // 生成签名，防止数据被篡改
        final String digestHex = getDigestSign(userUpdateParam.getId() + ":" + userUpdateParam.getDepartmentId(), SALT);
        param.put("sign", digestHex);
        final HttpResponse execute = HttpRequest.put(flowableServiceUI + "/updateUser")
                .addHeaders(param)
                .charset(StandardCharsets.UTF_8)
                .execute();
        if (execute.getStatus() != 200) {
            log.error("flowable 同步更新用户部门信息失败：{}", execute.body());
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "用户更新失败！");
        }
        // 删除用户与部门的关联
        userMapper.deleteUserAndDeptRelation(userUpdateParam.getId());
        // 新增用户与部门的关联
        departmentMapper.insertUserAndDepartmentRelation(userUpdateParam.getId(), Long.valueOf(userUpdateParam.getDepartmentId()));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUser(final UserAddParam userAddParam) {
        if (!StringUtils.equals(userAddParam.getUserPassword(), userAddParam.getCheckPassword())) {
            throw new BusinessException("两次密码输入不一致！");
        }
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.lambda().eq(UserEntity::getDeletedFlag, "false")
                .eq(UserEntity::getUserAccount, userAddParam.getUserAccount());
        final UserEntity existUserEntity = userMapper.selectOne(userEntityQueryWrapper);

        ThrowUtils.throwIf(null != existUserEntity, "账号已经存在！");

        UserEntity userEntity = BeanUtil.copyProperties(userAddParam, UserEntity.class);
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userEntity.getUserPassword()).getBytes());
        userEntity.setUserPassword(encryptPassword);
        // 设置默认用户信息

        userEntity.setUserAvatar(fileService.transferToFinalPath(userAddParam.getUserAvatar()));
        // 注册时，分配密钥
        userEntity.setAccessKey(DigestUtil.md5Hex(SALT + userAddParam.getUserAccount() + RandomUtil.randomNumbers(5)));
        userEntity.setSecretKey(DigestUtil.md5Hex(SALT + userAddParam.getUserAccount() + RandomUtil.randomNumbers(8)));
        userMapper.insert(userEntity);
        // 新增用户部门关联关系
        final Boolean flag = departmentMapper.insertUserAndDepartmentRelation(userEntity.getId(), Long.valueOf(userAddParam.getDepartmentId()));
        userEntity.setUserPassword(userAddParam.getUserPassword());
        userEntity.setAccessKey(null);
        userEntity.setSecretKey(null);
        userEntity.setUserAvatar(null);
        // 若不为注册， flowable 需要同步用户部门信息
        userEntity.setDepartmentId(Long.valueOf(userAddParam.getDepartmentId()));
        extractedSyncUserInfo(userEntity);
        return flag;
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

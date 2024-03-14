package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.myapiclientsdk.client.MyApiClient;
import com.christer.myapiclientsdk.model.User;
import com.christer.myapicommon.model.dto.interfaceinfo.InterfaceInfoApplyParam;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.myapicommon.model.entity.UserEntity;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.InterfaceInfoMapper;
import com.christer.project.mapper.UserMapper;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoInvokeParam;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;

import com.christer.project.model.enums.InterfaceInfoStatusEnum;
import com.christer.project.service.InterfaceInfoService;
import com.christer.project.util.ValidateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 11:19
 * Description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final UserMapper userMapper;

    @Value("${default-flowable-ui-service}")
    private String flowableServiceUI;
    @Override
    public boolean addInterFaceInfo(InterfaceInfoParam interfaceInfo) {
        InterfaceInfo info = BeanUtil.copyProperties(interfaceInfo, InterfaceInfo.class);
        final int insert = interfaceInfoMapper.insert(info);
        return insert > 0;
    }

    @Override
    public Page<InterfaceInfo> queryByPage(QueryInterfaceInfoParam interfaceInfoParam) {
        //分页参数
        final Page<InterfaceInfo> rowPage = new Page<>(interfaceInfoParam.getCurrentPage(), interfaceInfoParam.getPageSize());
        final LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(interfaceInfoParam.getName()), InterfaceInfo::getName, interfaceInfoParam.getName())
                .like(StringUtils.hasText(interfaceInfoParam.getDescription()), InterfaceInfo::getDescription, interfaceInfoParam.getDescription())
                .eq(StringUtils.hasText(interfaceInfoParam.getUrl()), InterfaceInfo::getUrl, interfaceInfoParam.getUrl())
                .eq(StringUtils.hasText(interfaceInfoParam.getMethod()), InterfaceInfo::getMethod, interfaceInfoParam.getMethod())
                .eq(!Objects.isNull(interfaceInfoParam.getStatus()), InterfaceInfo::getStatus, interfaceInfoParam.getStatus());
        return interfaceInfoMapper.selectPage(rowPage, queryWrapper);
    }

    @Override
    public InterfaceInfo queryById(Long id) {
        return interfaceInfoMapper.selectById(id);
    }

    @Override
    public boolean editInterfaceInto(InterfaceInfoParam interfaceInfo) {
        InterfaceInfo info = BeanUtil.copyProperties(interfaceInfo, InterfaceInfo.class);
        final int update = interfaceInfoMapper.updateById(info);
        return update > 0;
    }

    @Override
    public boolean deleteById(Long id, Long currentUserId) {
        return interfaceInfoMapper.deleteById(id) > 0;
    }

    @Override
    public boolean onlineInterfaceInfo(Long id, Long currentUserId) {
        Objects.requireNonNull(id, "接口id不存在！");
        Objects.requireNonNull(currentUserId, "当前用户id不存在！");
        // 获取接口信息
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        final MyApiClient myApiClient = getMyApiClient(currentUserId);
        // 判断接口是否能够调用
        User user = new User();
        user.setUsername("加油上线！！！");
        String result = myApiClient.getUsernameByPost(user);
        if (!StringUtils.hasText(result)) {
            throw new BusinessException(ResultCode.FAILED, "接口调用失败！");
        }
        // 修改接口状态
        InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
        updateInterfaceInfo.setId(id);
        updateInterfaceInfo.setUpdateUserId(currentUserId);
        updateInterfaceInfo.setUpdateTime(new Date());
        updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getCode());
        return interfaceInfoMapper.updateById(updateInterfaceInfo) > 0;
    }

    @NotNull
    private MyApiClient getMyApiClient(Long currentUserId) {
        // 获取用户信息
        final UserEntity userEntity = userMapper.selectById(currentUserId);
        ThrowUtils.throwIf(null == userEntity, "用户信息不存在！");
        // 根据用户信息获取 accessKey 和 secretKey
        // 获取api客户端并调用接口
        return new MyApiClient(userEntity.getAccessKey(), userEntity.getSecretKey());
    }

    @Override
    public boolean outlineInterfaceInfo(Long id, Long currentUserId) {
        Objects.requireNonNull(id, "接口id不存在！");
        Objects.requireNonNull(currentUserId, "当前用户id不存在！");
        // 获取接口信息
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(id);
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        // 判断接口是否能够调用
        User user = new User();
        user.setUsername("加油下线！！！");
        final MyApiClient myApiClient = getMyApiClient(currentUserId);
        String result = myApiClient.getUsernameByPost(user);
        if (!StringUtils.hasText(result)) {
            throw new BusinessException(ResultCode.FAILED, "接口调用失败！");
        }
        // 修改接口状态
        InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
        updateInterfaceInfo.setId(id);
        updateInterfaceInfo.setUpdateUserId(currentUserId);
        updateInterfaceInfo.setUpdateTime(new Date());
        updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getCode());
        return interfaceInfoMapper.updateById(updateInterfaceInfo) > 0;
    }

    @Override
    public Object invokeInterfaceInfo(InterfaceInfoInvokeParam param, Long currentUserId) {
        // 业务层校验
        ValidateUtil.validateBean(param);
        Objects.requireNonNull(currentUserId, "当前用户信息不存在！");
        // 根据接口id获取接口信息
        final InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(param.getId());
        ThrowUtils.throwIf(null == interfaceInfo, "接口信息不存在！");
        // 判断接口是否已经下线
        ThrowUtils.throwIf(InterfaceInfoStatusEnum.OFFLINE.getCode().equals(interfaceInfo.getStatus()), "该接口已下线，无法调用！");
        // 获取当前登录用户的密钥
        final UserEntity userEntity = userMapper.selectById(currentUserId);
        final String accessKey = userEntity.getAccessKey();
        final String secretKey = userEntity.getSecretKey();
        final MyApiClient myClient = new MyApiClient(accessKey, secretKey);
        // 调用接口
        // TODO 调用接口的逻辑必须修改
        final Gson gson = new Gson();
        User user = null;
        try {
            user = gson.fromJson(param.getUserRequestParams(), User.class);
        } catch (JsonSyntaxException e) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "请按照规范填写请求参数！");
        }
        final String usernameByPost = myClient.getUsernameByPost(user);
        log.info("调用结果：{}", usernameByPost);
        return usernameByPost;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyInterfaceInfo(final InterfaceInfoApplyParam param) {
        // 参数校验
        ValidateUtil.validateBean(param);
        // 根据流程的key 启动工作流
        final HashMap<String, String> headParam = new HashMap<>();
        headParam.put("processInstanceKey", "xxx");
        headParam.put("assigneeUser", String.valueOf(param.getCreateUserId()));

        final HttpResponse response = HttpRequest.post(flowableServiceUI + "/startAndCompleteTask")
                .addHeaders(headParam)
                .execute();
        if (response.getStatus() != 200) {
            throw new BusinessException("工作流启动失败！");
        }
        final String body = response.body();

        // 工作流返回结果
        // 新增接口申请记录
        // 新增接口审核记录

        return false;
    }
}

package com.christer.project.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.ThrowUtils;
import com.christer.project.mapper.UserInterfaceInfoMapper;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoListParam;
import com.christer.project.model.dto.userinterfaceinfo.QueryUserInterfaceInfoParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddParam;
import com.christer.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateParam;
import com.christer.project.model.entity.InterfaceInfo;
import com.christer.myapicommon.model.entity.UserInterfaceInfo;
import com.christer.project.service.UserInterfaceInfoService;
import com.christer.project.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Christer
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-02-09 15:00:15
 */
@Service
@RequiredArgsConstructor
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    private final UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public Page<UserInterfaceInfo> queryByPage(QueryUserInterfaceInfoParam interfaceInfoParam) {
        // 防止爬虫
        ThrowUtils.throwIf(interfaceInfoParam.getPageSize() > 50, "请求参数错误！");
        final Page<UserInterfaceInfo> rowPage = new Page<>(interfaceInfoParam.getCurrentPage(), interfaceInfoParam.getPageSize());
        final LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!Objects.isNull(interfaceInfoParam.getStatus())) {
            queryWrapper.eq(UserInterfaceInfo::getStatus, interfaceInfoParam.getStatus());
        }
        return userInterfaceInfoMapper.selectPage(rowPage, queryWrapper);
    }

    @Override
    public UserInterfaceInfo queryById(Long id) {
        Objects.requireNonNull(id, "用户接口信息关系id不存在！");
        return userInterfaceInfoMapper.selectById(id);
    }

    @Override
    public boolean addUserInterFaceInfo(UserInterfaceInfoAddParam userInterfaceInfo) {
        // 业务层校验
        ValidateUtil.validateBean(userInterfaceInfo);
        UserInterfaceInfo interfaceInfo = BeanUtil.copyProperties(userInterfaceInfo, UserInterfaceInfo.class);
        return userInterfaceInfoMapper.insert(interfaceInfo) > 0;
    }

    @Override
    public boolean updateUserInterFaceInfo(UserInterfaceInfoUpdateParam userInterfaceInfoUpdateParam) {
        // 业务层校验
        ValidateUtil.validateBean(userInterfaceInfoUpdateParam);
        UserInterfaceInfo userInterfaceInfo = BeanUtil.copyProperties(userInterfaceInfoUpdateParam, UserInterfaceInfo.class);
        return userInterfaceInfoMapper.updateById(userInterfaceInfo) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "用户接口信息关系id不存在！");
        return userInterfaceInfoMapper.deleteById(id) > 0;
    }

    @Override
    public List<UserInterfaceInfo> selectListByCondition(QueryUserInterfaceInfoListParam param) {
        final LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Objects.isNull(param.getStatus()), UserInterfaceInfo::getStatus, param.getStatus());
        return userInterfaceInfoMapper.selectList(queryWrapper);

    }
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invokeCount(Long interfaceInfoId, Long userId) {
        // 业务层校验
        Objects.requireNonNull(interfaceInfoId, ResultCode.PARAMS_ERROR.getMessage());
        Objects.requireNonNull(userId, ResultCode.PARAMS_ERROR.getMessage());
        final LambdaQueryWrapper<UserInterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .eq(UserInterfaceInfo::getUserId, userId);
        UserInterfaceInfo currentInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        ThrowUtils.throwIf(currentInfo == null, "当前用户调用接口信息不存在！");
        ThrowUtils.throwIf(Objects.requireNonNull(currentInfo).getLeftNum() <= 0, "该接口剩余调用次数不足！");
        int update = 0;
        // 加锁
        final String key = interfaceInfoId + ":" + userId;
        locks.computeIfAbsent(key, k -> new Object());
        synchronized(locks.get(key)) {
            // 更新接口调用次数
            LambdaUpdateWrapper<UserInterfaceInfo> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                    .eq(UserInterfaceInfo::getUserId, userId)
                    .setSql("left_num = left_num - 1, total_num = total_num + 1");
             update = userInterfaceInfoMapper.update(null, wrapper);
        }
        return update > 0;
    }
}





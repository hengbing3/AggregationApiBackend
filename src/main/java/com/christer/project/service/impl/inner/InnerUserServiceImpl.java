package com.christer.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.christer.myapicommon.model.entity.UserEntity;
import com.christer.myapicommon.service.InnerUserService;
import com.christer.project.common.ResultCode;
import com.christer.project.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 14:51
 * Description:
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserEntity getInvokeUserByCondition(String accessKey) {
        Objects.requireNonNull(accessKey, ResultCode.PARAMS_ERROR.getMessage());
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getAccessKey, accessKey);
        return userMapper.selectOne(queryWrapper);
    }
}

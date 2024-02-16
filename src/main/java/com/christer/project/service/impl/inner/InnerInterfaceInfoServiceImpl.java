package com.christer.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.myapicommon.service.InnerInterfaceInfoService;
import com.christer.project.common.ResultCode;
import com.christer.project.mapper.InterfaceInfoMapper;

import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 14:50
 * Description:
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfoByCondition(String url, String method) {
        Objects.requireNonNull(url, ResultCode.PARAMS_ERROR.getMessage());
        Objects.requireNonNull(method, ResultCode.PARAMS_ERROR.getMessage());
        final LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceInfo::getUrl, url)
                .eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }
}

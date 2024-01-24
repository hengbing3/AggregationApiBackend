package com.christer.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.christer.project.mapper.InterfaceInfoMapper;
import com.christer.project.model.dto.interfaceinfo.InterfaceInfoParam;
import com.christer.project.model.dto.interfaceinfo.QueryInterfaceInfoParam;
import com.christer.project.model.entity.InterfaceInfo;
import com.christer.project.service.InterfaceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-24 11:19
 * Description:
 */
@Service
@RequiredArgsConstructor
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService{

    private final InterfaceInfoMapper interfaceInfoMapper;

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
}

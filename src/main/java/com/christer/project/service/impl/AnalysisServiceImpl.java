package com.christer.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.christer.project.mapper.InterfaceInfoMapper;
import com.christer.project.mapper.UserInterfaceInfoMapper;

import com.christer.myapicommon.model.entity.InterfaceInfo;
import com.christer.myapicommon.model.entity.UserInterfaceInfo;
import com.christer.project.model.vo.InterfaceInfoVO;
import com.christer.project.service.AnalysisService;
import com.christer.project.util.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 21:41
 * Description:
 * 统计分析-实现层
 */
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public List<InterfaceInfoVO> listTopInvokeInterfaceInfo() {
        // 获取用户调用接口统计列表
        final List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        if (CollectionUtils.isEmpty(userInterfaceInfos)) {
            return Collections.emptyList();
        }
        // 根据接口id,查询接口信息
        Map<Long, List<UserInterfaceInfo>> listMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, listMap.keySet());
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(interfaceInfos)) {
            return Collections.emptyList();
        }
        // 拼装VO类返回
        List<InterfaceInfoVO> interfaceInfoVOS = BeanCopyUtil.copyListProperties(interfaceInfos, InterfaceInfoVO::new);
        for (InterfaceInfoVO interfaceInfoVO : interfaceInfoVOS) {
            UserInterfaceInfo userInterfaceInfo = listMap.get(interfaceInfoVO.getId()).get(0);
            interfaceInfoVO.setTotalNum(userInterfaceInfo.getTotalNum());
        }
        return interfaceInfoVOS;
    }
}

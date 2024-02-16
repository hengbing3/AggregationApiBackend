package com.christer.project.service;

import com.christer.project.model.vo.InterfaceInfoVO;

import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 21:40
 * Description:
 */
public interface AnalysisService {
    /**
     * 获取接口调用次数统计
     * @return
     */
    List<InterfaceInfoVO> listTopInvokeInterfaceInfo();
}

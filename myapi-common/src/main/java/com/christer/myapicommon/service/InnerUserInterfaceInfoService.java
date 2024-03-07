package com.christer.myapicommon.service;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 13:47
 * Description:
 * 内部用户调用接口服务
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 用户接口调用次数统计
     *
     * @param interfaceInfoId 接口id
     * @param userId          用户id
     * @return true : 成功 false: 失败
     */
    boolean invokeCount(Long interfaceInfoId, Long userId);
}

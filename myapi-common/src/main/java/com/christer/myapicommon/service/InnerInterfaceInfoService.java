package com.christer.myapicommon.service;

import com.christer.myapicommon.model.entity.InterfaceInfo;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 13:47
 * Description:
 * 内部接口信息服务
 */
public interface InnerInterfaceInfoService {

    /**
     * 查询调用的接口是否存在
     * @param url 请求url
     * @param method 请求方法 GET,POST...
     * @return obj
     */
    InterfaceInfo getInterfaceInfoByCondition(String url, String method);
}

package com.christer.myapicommon.service;

import com.christer.myapicommon.model.entity.UserEntity;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-16 13:47
 * Description:
 * 内部用户服务
 */
public interface InnerUserService {


    /**
     * 数据库是否给用户分配密钥
     * @param accessKey 访问密钥
     * @return obj
     */
    UserEntity getInvokeUserByCondition(String accessKey);

}

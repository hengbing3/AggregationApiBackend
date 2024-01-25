package com.christer.project.controller;

import com.christer.project.config.SessionServiceConfig;
import com.christer.project.model.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-01-25 21:28
 * Description:
 */
@Component
public class AbstractSessionController {

    private SessionServiceConfig sessionServiceConfig;

    /**
     * 获取当前登录用户信息
     * @return userInfo
     */
    public UserInfoVO getCurrentUserInfo() {
        return sessionServiceConfig.getCurrentUserInfo();
    }

    /**
     * 获取当前登录用户id
     * @return 用户id
     */
    public Long getCurrentUserId() {
        return sessionServiceConfig.getCurrentUserInfo().getId();
    }


    @Autowired
    public AbstractSessionController setSessionObjectService(@Qualifier("sessionServiceConfig") SessionServiceConfig sessionObjectService) {
        this.sessionServiceConfig = sessionObjectService;
        return this;
    }
}

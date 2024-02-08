package com.christer.project.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-07 12:18
 * Description:
 */
@Component
public class StpInterfaceConfig implements StpInterface {

    private final SessionServiceConfig sessionServiceConfig;

    public StpInterfaceConfig(SessionServiceConfig sessionServiceConfig) {
        this.sessionServiceConfig = sessionServiceConfig;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> userPermissionList = sessionServiceConfig.getCurrentUserInfo().getUserPermissionList();
        return CollectionUtils.isEmpty(userPermissionList) ? Collections.emptyList() : userPermissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.singletonList(sessionServiceConfig.getCurrentUserInfo().getUserRole());
    }
}

package com.christer.project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-09 17:37
 * Description:
 */
@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Test
    void invokeCount() {
        boolean invokeCount = userInterfaceInfoService.invokeCount(1L, 1L);
        Assertions.assertTrue(invokeCount);
    }
}
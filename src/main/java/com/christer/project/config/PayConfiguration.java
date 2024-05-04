package com.christer.project.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.christer.project.common.ResultCode;
import com.christer.project.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-05-04 13:25
 * Description:
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class PayConfiguration {

    private final AliPayAccountConfig aliPayConfig;


    @Bean
    public AlipayClient initAliPayConfig() {
        try {
            return new DefaultAlipayClient(aliPayConfig);
        } catch (AlipayApiException e) {
            log.error("支付宝支付配置创建失败:{0}", e);
            throw new BusinessException(ResultCode.ALIPAY_CONFIG_ERROR, ResultCode.ALIPAY_CONFIG_ERROR.getMessage());
        }
    }

}

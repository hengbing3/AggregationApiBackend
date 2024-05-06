package com.christer.project.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.christer.project.config.AliPayAccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-05-04 13:55
 * Description:
 * 支付宝支付单元测试
 */
@SpringBootTest
@Slf4j
class AliPayTest {

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private AliPayAccountConfig aliPayAccountConfig;

    /**
     * 预创建订单
     */
    @Test
    void testPreCreateOrder() {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo("20240504010101001");
        model.setTotalAmount("0.01");
        model.setSubject("Watch test");
        // 设置订单超时时间15min
        model.setTimeoutExpress("15m");
        model.setQrCodeTimeoutExpress("15m");
        request.setBizModel(model);
        request.setNotifyUrl(aliPayAccountConfig.getNotifyUrl());
        request.setReturnUrl(aliPayAccountConfig.getReturnUrl());
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            log.info("response:{}", response.getBody());
            Assertions.assertTrue(response.isSuccess());
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询订单
     */
    @Test
    void testQueryOrder() throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo("20240504010101001");
        request.setBizModel(model);

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        final String tradeStatus = response.getTradeStatus();
        log.info("订单状态:{}", tradeStatus);

    }

    /**
     * 关闭交易
     */
    @Test
    void closeOrder() throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo("20240504010101001");
        request.setBizModel(model);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        log.info("结果:{}", response.isSuccess());
    }
}

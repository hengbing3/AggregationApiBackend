package com.christer.project.config;

import com.alipay.api.AlipayConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-05-04 10:46
 * Description:
 * 支付宝支付配置
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "alipay")
@Component
public class AliPayAccountConfig extends AlipayConfig {
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 支付宝公钥字符串
     */
    private String alipayPublicKey;
    /**
     * 网关地址
     * 线上：https://openapi.alipay.com/gateway.do
     * 沙箱：https://openapi.alipaydev.com/gateway.do
     */
    private String serverUrl;
    /**
     * 开放平台上创建的应用的ID
     */
    private String appId;
    /**
     * 报文格式
     */
    private String format;
    /**
     * 字符串编码
     */
    private String charset;
    /**
     * 签名算法类型
     */
    private String signType;
}

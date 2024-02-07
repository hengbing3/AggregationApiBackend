package com.christer.myinterface.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-06 17:05
 * Description:
 */
public class SignUtils {
    /**
     * 生成签名认证算法
     *
     * @param body   包含需要签名的参数的哈希映射
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
        // 使用SHA256算法的Digester
        final Digester digester = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        final String content = body + secretKey;
        return digester.digestHex(content);
    }
}

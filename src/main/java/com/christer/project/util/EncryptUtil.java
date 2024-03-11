package com.christer.project.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;

import static com.christer.project.constant.CommonConstant.SALT;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-10 20:51
 * Description:
 * 加密工具类
 */

public class EncryptUtil {


    private static RSA rsa = null;

    static {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYrF42hh7Jg08Xko8TEanbH3p3kynY7xCZzlz97rwTIRTO4IglNvWMLIYAkW9JKtcWculN09eFM2NZICxqVE4XxFtkR3MbbhVSBGCaZOdAAmVoeXaobxREmC9WdCYl0Tc3XyR+acfud8dpzvJJyULtj59CuzCoQN5pcYmYKnGtNQIDAQAB";
        rsa = new RSA("RSA/ECB/PKCS1Padding",null, publicKey);
    }
    private EncryptUtil() {

    }

    /**
     * 生成摘要签名
     * @param param 请求参数
     * @return 单向加密数据
     */
    public static String getDigestSign(String param) {
        final Digester digester = DigestUtil.digester(DigestAlgorithm.SHA256);
        // 添加签名到请求头，保证传参不被修改
        return digester.digestHex(param + SALT);
    }

    /**
     * 生成rsa 加密数据
     * @param jsonStr 需要加密json
     * @return str
     */
    public static String generateRsaEncryptData(String jsonStr) {
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(jsonStr, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return HexUtil.encodeHexStr(encrypt);
    }


}

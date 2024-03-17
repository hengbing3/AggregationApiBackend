package com.christer.project;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringbootApiBackendApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testAddFlowGroup() {
         Map<String, Object> params = new HashMap<>();
         params.put("deptId", "1");
         params.put("deptName", "API开放平台");
         params.put("type", "开放");
        HttpResponse httpResponse = HttpRequest.post("http://localhost:6699/flowable-ui/userGroup")
                .form(params)
                .charset(StandardCharsets.UTF_8)
                .execute();
        Assertions.assertEquals(200,httpResponse.getStatus());
    }

    @Test
    void testEncrypt() {
        // 1. 利用空构造器的RSA获取Base64位的publicKey, privateKey
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        System.out.println("公钥:" + publicKeyBase64);
        System.out.println("--------------------------");
        System.out.println("私钥:" + privateKeyBase64);
        // 2. 根据公钥生成密文
        RSA rsa1 = new RSA(null, publicKeyBase64);
        byte[] encrypt = rsa1.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        String hexStr = HexUtil.encodeHexStr(encrypt);

        // 3. 根据私钥解密密文
        RSA rsa2 = new RSA(privateKeyBase64, null);
        byte[] decodeHex = HexUtil.decodeHex(hexStr);
        byte[] decrypt1 = rsa2.decrypt(decodeHex, KeyType.PrivateKey);
        System.out.println(StrUtil.str(decrypt1, CharsetUtil.CHARSET_UTF_8));
        // 指定文件路径
        String filePath = "static/publicKey.txt";
        String filePath2 = "static/privateKey.txt";
        try {
            // 获取文件资源
            Resource resource = new ClassPathResource(filePath);

            // 获取文件对象
            File file = resource.getFile();

            // 写入字符串到文件
            FileOutputStream fos = new FileOutputStream(file);
            FileCopyUtils.copy(publicKeyBase64.getBytes(), fos);
            fos.close();

            Resource resource2 = new ClassPathResource(filePath2);
            final File file1 = resource2.getFile();
            final FileOutputStream fileOutputStream = new FileOutputStream(file1);
            FileCopyUtils.copy(privateKeyBase64.getBytes(), fileOutputStream);
            fileOutputStream.close();

            System.out.println("字符串已保存到 public.txt 文件中");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

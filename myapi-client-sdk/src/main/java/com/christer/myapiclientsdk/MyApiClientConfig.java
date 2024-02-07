package com.christer.myapiclientsdk;


import com.christer.myapiclientsdk.client.MyApiClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-02-06 21:48
 * Description:
 */
@Configuration
@ConfigurationProperties("myapi.client")
@ComponentScan
public class MyApiClientConfig {

    private String accessKey;

    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Bean
    public MyApiClient myApiClient() {
        return new MyApiClient(accessKey, secretKey);
    }
}


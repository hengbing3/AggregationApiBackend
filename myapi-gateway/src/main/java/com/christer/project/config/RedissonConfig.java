package com.christer.project.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-20 16:37
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host;

    private String port;

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置
        final Config config = new Config();
        // 集群模式
        // config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
        final String redisAddress = String.format("redis://%s:%s", host, port);
        // 单机模式
        config.useSingleServer().setAddress(redisAddress).setDatabase(4);
        // 2. 创建实例
        return Redisson.create(config);
    }
}

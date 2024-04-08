package com.christer.project;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-08 21:40
 * Description:
 * 针对不同的ip地址采取的限流策略
 */
@Configuration
public class GatewayConfig {

    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }
}

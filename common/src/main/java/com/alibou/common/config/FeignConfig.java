package com.alibou.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public CustomFeignErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}

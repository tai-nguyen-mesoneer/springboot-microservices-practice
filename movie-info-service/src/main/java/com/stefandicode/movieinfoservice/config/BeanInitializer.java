package com.stefandicode.movieinfoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanInitializer {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }
}

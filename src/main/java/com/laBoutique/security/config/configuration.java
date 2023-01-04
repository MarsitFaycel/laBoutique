package com.laBoutique.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class configuration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

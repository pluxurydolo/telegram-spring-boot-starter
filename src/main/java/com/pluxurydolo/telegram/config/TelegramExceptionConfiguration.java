package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.exception.TelegramExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramExceptionConfiguration {

    @Bean
    public TelegramExceptionHandler telegramExceptionHandler() {
        return new TelegramExceptionHandler();
    }
}

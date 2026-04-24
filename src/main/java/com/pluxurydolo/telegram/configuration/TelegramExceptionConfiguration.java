package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.exception.handler.TelegramExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramExceptionConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TelegramExceptionHandler telegramExceptionHandler() {
        return new TelegramExceptionHandler();
    }
}

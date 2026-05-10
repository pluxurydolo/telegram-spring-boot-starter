package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.handler.executor.AbstractHandlerExecutor;
import com.pluxurydolo.telegram.listener.TelegramUpdatesListener;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TelegramUpdateConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TelegramUpdatesListener telegramUpdatesListener(
        FilterExecutor filterExecutor,
        List<AbstractHandlerExecutor> executors,
        TelegramUpdateParser telegramUpdateParser
    ) {
        return new TelegramUpdatesListener(filterExecutor, executors, telegramUpdateParser);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramUpdateParser telegramUpdateParser() {
        return new TelegramUpdateParser();
    }
}

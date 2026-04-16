package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.filter.Filter;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.filter.SenderFilter;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import com.pluxurydolo.telegram.properties.TelegramProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TelegramFilterConfiguration {

    @Bean
    public FilterExecutor filterExecutor(List<Filter> filters) {
        return new FilterExecutor(filters);
    }

    @Bean
    @ConditionalOnProperty(prefix = "telegram.filter.sender", name = "enabled", havingValue = "true")
    public SenderFilter senderFilter(TelegramUpdateParser telegramUpdateParser, TelegramProperties telegramProperties) {
        long allowedUserId = telegramProperties.allowedUserId();
        return new SenderFilter(telegramUpdateParser, allowedUserId);
    }
}

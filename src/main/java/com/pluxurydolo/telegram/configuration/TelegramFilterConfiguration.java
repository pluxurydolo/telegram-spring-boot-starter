package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.filter.Filter;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.filter.SenderFilter;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TelegramFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FilterExecutor filterExecutor(List<Filter> filters) {
        return new FilterExecutor(filters);
    }

    @Bean
    @ConditionalOnProperty(prefix = "telegram.filter.by-user-id", name = "enabled", havingValue = "true")
    public SenderFilter senderFilter(
        TelegramFilterProperties telegramFilterProperties,
        TelegramUpdateParser telegramUpdateParser
    ) {
        return new SenderFilter(telegramFilterProperties, telegramUpdateParser);
    }
}

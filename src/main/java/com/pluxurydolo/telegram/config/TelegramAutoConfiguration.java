package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.exception.TelegramExceptionHandler;
import com.pluxurydolo.telegram.filter.Filter;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.filter.SenderFilter;
import com.pluxurydolo.telegram.handler.executor.AbstractHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.CommandHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.MediaHandlerExecutor;
import com.pluxurydolo.telegram.handler.media.AbstractMediaHandler;
import com.pluxurydolo.telegram.handler.text.AbstractCommandHandler;
import com.pluxurydolo.telegram.listener.TelegramUpdatesListener;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import com.pluxurydolo.telegram.properties.FilterProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
@EnableConfigurationProperties(FilterProperties.class)
public class TelegramAutoConfiguration {
    private final FilterProperties filterProperties;

    public TelegramAutoConfiguration(FilterProperties filterProperties) {
        this.filterProperties = filterProperties;
    }

    @Bean
    public TelegramUpdatesListener telegramUpdatesListener(
        FilterExecutor filterExecutor,
        List<AbstractHandlerExecutor> executors
    ) {
        return new TelegramUpdatesListener(filterExecutor, executors);
    }

    @Bean
    public TelegramClient telegramClient() {
        return new TelegramClient();
    }

    @Bean
    public CommandHandlerExecutor commandHandlerExecutor(List<AbstractCommandHandler> handlers) {
        return new CommandHandlerExecutor(handlers);
    }

    @Bean
    public MediaHandlerExecutor mediaHandlerExecutor(List<AbstractMediaHandler> handlers) {
        return new MediaHandlerExecutor(handlers);
    }

    @Bean
    public FilterExecutor filterExecutor(List<Filter> filters) {
        return new FilterExecutor(filters);
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "telegram.filter",
        name = "enabled",
        havingValue = "true"
    )
    public SenderFilter senderFilter(TelegramUpdateParser telegramUpdateParser) {
        long allowedUserId = filterProperties.allowedUserId();
        return new SenderFilter(telegramUpdateParser, allowedUserId);
    }

    @Bean
    public TelegramUpdateParser telegramUpdateParser() {
        return new TelegramUpdateParser();
    }

    @Bean
    public TelegramExceptionHandler telegramExceptionHandler() {
        return new TelegramExceptionHandler();
    }
}

package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.handler.executor.AbstractHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.CommandHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.MediaHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.TextHandlerExecutor;
import com.pluxurydolo.telegram.handler.media.AbstractMediaHandler;
import com.pluxurydolo.telegram.handler.text.AbstractCommandHandler;
import com.pluxurydolo.telegram.handler.text.AbstractTextHandler;
import com.pluxurydolo.telegram.listener.TelegramUpdatesListener;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TelegramUpdateConfiguration {

    @Bean
    public TelegramUpdatesListener telegramUpdatesListener(
        FilterExecutor filterExecutor,
        List<AbstractHandlerExecutor> executors
    ) {
        return new TelegramUpdatesListener(filterExecutor, executors);
    }

    @Bean
    public PerUserRateLimiter perUserRateLimiter(
        TelegramRateLimitProperties telegramRateLimitProperties,
        TelegramTextClient telegramTextClient
    ) {
        return new PerUserRateLimiter(telegramRateLimitProperties, telegramTextClient);
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
    public TextHandlerExecutor textHandlerExecutor(List<AbstractTextHandler> handlers) {
        return new TextHandlerExecutor(handlers);
    }

    @Bean
    public TelegramUpdateParser telegramUpdateParser() {
        return new TelegramUpdateParser();
    }
}

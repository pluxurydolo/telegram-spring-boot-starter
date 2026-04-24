package com.pluxurydolo.telegram.configuration;

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
        List<AbstractHandlerExecutor> executors
    ) {
        return new TelegramUpdatesListener(filterExecutor, executors);
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandHandlerExecutor commandHandlerExecutor(List<AbstractCommandHandler> handlers) {
        return new CommandHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public MediaHandlerExecutor mediaHandlerExecutor(List<AbstractMediaHandler> handlers) {
        return new MediaHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public TextHandlerExecutor textHandlerExecutor(List<AbstractTextHandler> handlers) {
        return new TextHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramUpdateParser telegramUpdateParser() {
        return new TelegramUpdateParser();
    }
}

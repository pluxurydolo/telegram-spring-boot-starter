package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.handler.callback.AbstractCallbackHandler;
import com.pluxurydolo.telegram.handler.executor.AudioHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.CallbackHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.CommandHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.ImageHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.TextHandlerExecutor;
import com.pluxurydolo.telegram.handler.executor.VideoHandlerExecutor;
import com.pluxurydolo.telegram.handler.media.AbstractAudioHandler;
import com.pluxurydolo.telegram.handler.media.AbstractImageHandler;
import com.pluxurydolo.telegram.handler.media.AbstractVideoHandler;
import com.pluxurydolo.telegram.handler.text.AbstractCommandHandler;
import com.pluxurydolo.telegram.handler.text.AbstractTextHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TelegramExecutorConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommandHandlerExecutor commandHandlerExecutor(List<AbstractCommandHandler> handlers) {
        return new CommandHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public TextHandlerExecutor textHandlerExecutor(List<AbstractTextHandler> handlers) {
        return new TextHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public CallbackHandlerExecutor callbackHandlerExecutor(List<AbstractCallbackHandler> handlers) {
        return new CallbackHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public ImageHandlerExecutor imageHandlerExecutor(List<AbstractImageHandler> handlers) {
        return new ImageHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoHandlerExecutor videoHandlerExecutor(List<AbstractVideoHandler> handlers) {
        return new VideoHandlerExecutor(handlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public AudioHandlerExecutor audioHandlerExecutor(List<AbstractAudioHandler> handlers) {
        return new AudioHandlerExecutor(handlers);
    }
}

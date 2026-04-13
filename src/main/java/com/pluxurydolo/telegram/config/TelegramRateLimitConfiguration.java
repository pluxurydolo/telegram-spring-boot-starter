package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import com.pluxurydolo.telegram.ratelimiter.handler.RateLimitExceededHandler;
import com.pluxurydolo.telegram.ratelimiter.message.RateLimitExceededMessageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramRateLimitConfiguration {

    @Bean
    public PerUserRateLimiter perUserRateLimiter(
        TelegramRateLimitProperties telegramRateLimitProperties,
        RateLimitExceededHandler rateLimitExceededHandler
    ) {
        return new PerUserRateLimiter(telegramRateLimitProperties, rateLimitExceededHandler);
    }

    @Bean
    public RateLimitExceededHandler rateLimitExceededHandler(
        TelegramTextClient telegramTextClient,
        TelegramRateLimitProperties telegramRateLimitProperties,
        RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder
    ) {
        return new RateLimitExceededHandler(
            telegramTextClient,
            telegramRateLimitProperties,
            rateLimitExceededMessageBuilder
        );
    }

    @Bean
    public RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder() {
        return new RateLimitExceededMessageBuilder();
    }
}

package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import com.pluxurydolo.telegram.ratelimiter.handler.RateLimitExceededHandler;
import com.pluxurydolo.telegram.ratelimiter.message.RateLimitExceededMessageBuilder;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static java.time.Duration.ZERO;

@Configuration
public class TelegramRateLimitConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PerUserRateLimiter perUserRateLimiter(
        RateLimiterConfig rateLimiterConfig,
        RateLimiterRegistry rateLimiterRegistry,
        RateLimitExceededHandler rateLimitExceededHandler
    ) {
        return new PerUserRateLimiter(rateLimiterConfig, rateLimiterRegistry, rateLimitExceededHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterRegistry rateLimiterRegistry(RateLimiterConfig rateLimiterConfig) {
        return RateLimiterRegistry.of(rateLimiterConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterConfig rateLimiterConfig(TelegramRateLimitProperties telegramRateLimitProperties) {
        int threshold = telegramRateLimitProperties.threshold();
        Duration refreshPeriod = telegramRateLimitProperties.refreshPeriod();

        return RateLimiterConfig.custom()
            .limitRefreshPeriod(refreshPeriod)
            .limitForPeriod(threshold)
            .timeoutDuration(ZERO)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean
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
    @ConditionalOnMissingBean
    public RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder() {
        return new RateLimitExceededMessageBuilder();
    }
}

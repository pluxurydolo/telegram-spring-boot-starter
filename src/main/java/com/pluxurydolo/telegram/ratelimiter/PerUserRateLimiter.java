package com.pluxurydolo.telegram.ratelimiter;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.ratelimiter.handler.RateLimitExceededHandler;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class PerUserRateLimiter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerUserRateLimiter.class);

    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final RateLimitExceededHandler rateLimitExceededHandler;

    public PerUserRateLimiter(
        RateLimiterConfig rateLimiterConfig,
        RateLimiterRegistry rateLimiterRegistry,
        RateLimitExceededHandler rateLimitExceededHandler
    ) {
        this.rateLimiterConfig = rateLimiterConfig;
        this.rateLimiterRegistry = rateLimiterRegistry;
        this.rateLimitExceededHandler = rateLimitExceededHandler;
    }

    public Mono<String> withRateLimit(Update update, TelegramBot telegramBot, Mono<String> operation) {
        Long userId = update.message().from().id();

        return operation.transformDeferred(mono -> applyRateLimiter(userId, mono))
            .onErrorResume(_ -> {
                LOGGER.error("iorw [telegram-starter] Превышен лимит запросов для пользователя: {}", userId);
                return rateLimitExceededHandler.handle(telegramBot);
            });
    }

    private <T> Mono<T> applyRateLimiter(long userId, Mono<T> mono) {
        String name = String.format("telegram-user-%s", userId);
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(name, rateLimiterConfig);
        RateLimiterOperator<T> transformer = RateLimiterOperator.of(rateLimiter);
        return mono.transformDeferred(transformer);
    }
}

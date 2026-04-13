package com.pluxurydolo.telegram.ratelimiter;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pluxurydolo.telegram.ratelimiter.handler.RateLimitExceededHandler;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class PerUserRateLimiterTests {

    @Mock
    private RateLimiterConfig rateLimiterConfig;

    @Mock
    private RateLimiterRegistry rateLimiterRegistry;

    @Mock
    private RateLimitExceededHandler rateLimitExceededHandler;

    @Mock
    private Update update;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private Message message;

    @Mock
    private User user;

    @Mock
    private RateLimiter rateLimiter;

    @InjectMocks
    private PerUserRateLimiter perUserRateLimiter;

    @Test
    void testWithRateLimit() {
        when(update.message())
            .thenReturn(message);
        when(message.from())
            .thenReturn(user);
        when(user.id())
            .thenReturn(1L);
        when(rateLimiterRegistry.rateLimiter(anyString(), any(RateLimiterConfig.class)))
            .thenReturn(rateLimiter);

        Mono<String> result = perUserRateLimiter.withRateLimit(update, telegramBot, Mono.just("operation"));

        create(result)
            .expectNext("operation")
            .verifyComplete();
    }

    @Test
    void testWithRateLimitWhenRateLimitExceeded() {
        doThrow(RuntimeException.class)
            .when(rateLimiterRegistry).rateLimiter(anyString(), any(RateLimiterConfig.class));
        when(update.message())
            .thenReturn(message);
        when(message.from())
            .thenReturn(user);
        when(user.id())
            .thenReturn(1L);
        when(rateLimitExceededHandler.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = perUserRateLimiter.withRateLimit(update, telegramBot, Mono.just("operation"));

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}

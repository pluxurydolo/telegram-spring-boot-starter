package com.pluxurydolo.telegram.ratelimiter.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import com.pluxurydolo.telegram.ratelimiter.message.RateLimitExceededMessageBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class RateLimitExceededHandlerTests {

    @Mock
    private TelegramTextClient telegramTextClient;

    @Mock
    private TelegramRateLimitProperties telegramRateLimitProperties;

    @Mock
    private RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private RateLimitExceededHandler rateLimitExceededHandler;

    @Test
    void testHandle() {
        when(telegramRateLimitProperties.refreshPeriod())
            .thenReturn(Duration.ofSeconds(2));
        when(rateLimitExceededMessageBuilder.build(anyLong()))
            .thenReturn("");
        when(telegramTextClient.sendText(any()))
            .thenReturn(Mono.just(1));
        when(telegramTextClient.sendEditText(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = rateLimitExceededHandler.handle(telegramBot);

        create(result)
            .expectNext("")
            .verifyComplete();
    }
}

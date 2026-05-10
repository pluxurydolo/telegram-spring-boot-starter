package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class AbstractUpdateHandlerTests {
    private static final TelegramTextClient TELEGRAM_TEXT_CLIENT = mock(TelegramTextClient.class);
    private static final PerUserRateLimiter PER_USER_RATE_LIMITER = mock(PerUserRateLimiter.class);
    private static final Update UPDATE = mock(Update.class);

    @Test
    void testHandle() {
        when(PER_USER_RATE_LIMITER.withRateLimit(any(), any(), any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = UPDATE_HANDLER.handle(UPDATE);

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        when(PER_USER_RATE_LIMITER.withRateLimit(any(), any(), any()))
            .thenReturn(Mono.error(new RuntimeException()));
        when(TELEGRAM_TEXT_CLIENT.sendText(any()))
            .thenReturn(Mono.just(1));

        Mono<String> result = UPDATE_HANDLER.handle(UPDATE);

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static final AbstractUpdateHandler UPDATE_HANDLER = new AbstractUpdateHandler(
        TELEGRAM_TEXT_CLIENT,
        PER_USER_RATE_LIMITER
    ) {

        @Override
        public boolean condition(Update update) {
            return false;
        }

        @Override
        public Mono<String> doWork(Update update) {
            return null;
        }

        @Override
        public UpdateType updateType() {
            return null;
        }

        @Override
        public String failMessage() {
            return "";
        }

        @Override
        public TelegramBot telegramBot() {
            return null;
        }

        @Override
        public Mono<String> onException(Throwable throwable) {
            return Mono.just("");
        }
    };
}

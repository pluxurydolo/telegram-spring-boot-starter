package com.pluxurydolo.telegram.handler.callback;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.CALLBACK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractCallbackHandlerTests {
    private static final TelegramTextClient TELEGRAM_TEXT_CLIENT = mock(TelegramTextClient.class);
    private static final PerUserRateLimiter PER_USER_RATE_LIMITER = mock(PerUserRateLimiter.class);
    private static final Update UPDATE = mock(Update.class);
    private static final CallbackQuery CALLBACK_QUERY = mock(CallbackQuery.class);

    @Test
    void testUpdateType() {
        UpdateType result = callbackHandler.updateType();

        assertThat(result)
            .isEqualTo(CALLBACK);
    }

    @Test
    void testCondition() {
        when(UPDATE.callbackQuery())
            .thenReturn(CALLBACK_QUERY);

        boolean result = callbackHandler.condition(UPDATE);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenCallbackQueryIsNull() {
        when(UPDATE.callbackQuery())
            .thenReturn(null);

        boolean result = callbackHandler.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    private AbstractCallbackHandler callbackHandler = new AbstractCallbackHandler(
        TELEGRAM_TEXT_CLIENT,
        PER_USER_RATE_LIMITER
    ) {

        @Override
        public Mono<String> doWork(Update u) {
            return null;
        }

        @Override
        public String failMessage() {
            return null;
        }

        @Override
        public TelegramBot telegramBot() {
            return null;
        }

        @Override
        public Mono<String> onException(Throwable throwable) {
            return null;
        }
    };
}

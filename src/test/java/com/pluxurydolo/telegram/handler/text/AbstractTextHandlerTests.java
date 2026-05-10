package com.pluxurydolo.telegram.handler.text;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.TEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractTextHandlerTests {
    private static final TelegramTextClient TELEGRAM_TEXT_CLIENT = mock(TelegramTextClient.class);
    private static final PerUserRateLimiter PER_USER_RATE_LIMITER = mock(PerUserRateLimiter.class);
    private static final Update UPDATE = mock(Update.class);
    private static final Message MESSAGE = mock(Message.class);

    @Test
    void testUpdateType() {
        UpdateType result = TEXT_HANDLER.updateType();

        assertThat(result)
            .isEqualTo(TEXT);
    }

    @Test
    void testCondition() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.text())
            .thenReturn("text");

        boolean result = TEXT_HANDLER.condition(UPDATE);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageIsNull() {
        when(UPDATE.message())
            .thenReturn(null);

        boolean result = TEXT_HANDLER.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    @Test
    void testConditionWhenMessageTextIsCommand() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.text())
            .thenReturn("/text");

        boolean result = TEXT_HANDLER.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    private static final AbstractTextHandler TEXT_HANDLER = new AbstractTextHandler(
        TELEGRAM_TEXT_CLIENT,
        PER_USER_RATE_LIMITER
    ) {

        @Override
        public Mono<String> doWork(Update update) {
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

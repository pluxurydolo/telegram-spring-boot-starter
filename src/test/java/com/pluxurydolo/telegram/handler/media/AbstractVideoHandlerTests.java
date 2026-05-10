package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Video;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.VIDEO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractVideoHandlerTests {
    private static final TelegramTextClient TELEGRAM_TEXT_CLIENT = mock(TelegramTextClient.class);
    private static final PerUserRateLimiter PER_USER_RATE_LIMITER = mock(PerUserRateLimiter.class);
    private static final Update UPDATE = mock(Update.class);
    private static final Message MESSAGE = mock(Message.class);
    private static final Video MOCK_VIDEO = mock(Video.class);

    @Test
    void testUpdateType() {
        UpdateType result = VIDEO_HANDLER.updateType();

        assertThat(result)
            .isEqualTo(VIDEO);
    }

    @Test
    void testCondition() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.video())
            .thenReturn(MOCK_VIDEO);

        boolean result = VIDEO_HANDLER.condition(UPDATE);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageIsNull() {
        when(UPDATE.message())
            .thenReturn(null);

        boolean result = VIDEO_HANDLER.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    @Test
    void testConditionWhenVideoIsNull() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.video())
            .thenReturn(null);

        boolean result = VIDEO_HANDLER.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    private static final AbstractVideoHandler VIDEO_HANDLER = new AbstractVideoHandler(
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

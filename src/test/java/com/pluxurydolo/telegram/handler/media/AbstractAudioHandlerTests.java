package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Audio;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.AUDIO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbstractAudioHandlerTests {
    private static final TelegramTextClient TELEGRAM_TEXT_CLIENT = mock(TelegramTextClient.class);
    private static final PerUserRateLimiter PER_USER_RATE_LIMITER = mock(PerUserRateLimiter.class);
    private static final Update UPDATE = mock(Update.class);
    private static final Message MESSAGE = mock(Message.class);
    private static final Audio MOCK_AUDIO = mock(Audio.class);

    @Test
    void testUpdateType() {
        UpdateType result = audioHandler.updateType();

        assertThat(result)
            .isEqualTo(AUDIO);
    }

    @Test
    void testCondition() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.audio())
            .thenReturn(MOCK_AUDIO);

        boolean result = audioHandler.condition(UPDATE);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageIsNull() {
        when(UPDATE.message())
            .thenReturn(null);

        boolean result = audioHandler.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    @Test
    void testConditionWhenAudioIsNull() {
        when(UPDATE.message())
            .thenReturn(MESSAGE);
        when(MESSAGE.audio())
            .thenReturn(null);

        boolean result = audioHandler.condition(UPDATE);

        assertThat(result)
            .isFalse();
    }

    private AbstractAudioHandler audioHandler = new AbstractAudioHandler(
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

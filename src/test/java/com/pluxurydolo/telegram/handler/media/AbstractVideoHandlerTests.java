package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Video;
import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.VIDEO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractVideoHandlerTests {
    private final AbstractVideoHandler videoHandler = videoHandler();

    @Mock
    private TelegramClient telegramClient;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private Video video;

    @Test
    void testUpdateType() {
        UpdateType result = videoHandler.updateType();

        assertThat(result)
            .isEqualTo(VIDEO);
    }

    @Test
    void testCondition() {
        when(update.message())
            .thenReturn(message);
        when(message.video())
            .thenReturn(video);

        boolean result = videoHandler.condition(update);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageDoesNotHaveVideo() {
        when(update.message())
            .thenReturn(message);
        when(message.video())
            .thenReturn(null);

        boolean result = videoHandler.condition(update);

        assertThat(result)
            .isFalse();
    }

    private AbstractVideoHandler videoHandler() {
        return new AbstractVideoHandler(telegramClient) {

            @Override
            protected Mono<String> doWork(Update tgUpdate) {
                return null;
            }

            @Override
            protected String failMessage() {
                return "";
            }

            @Override
            protected TelegramBot telegramBot() {
                return null;
            }

            @Override
            protected long recepientId() {
                return 0;
            }

            @Override
            protected Mono<String> onException(Throwable throwable) {
                return null;
            }
        };
    }
}

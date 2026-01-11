package com.pluxurydolo.telegram.handler.text;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.TEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractTextHandlerTests {
    private final AbstractTextHandler textHandler = textHandler();

    @Mock
    private TelegramClient telegramClient;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Test
    void testUpdateType() {
        UpdateType result = textHandler.updateType();

        assertThat(result)
            .isEqualTo(TEXT);
    }

    @Test
    void testCondition() {
        when(update.message())
            .thenReturn(message);
        when(message.text())
            .thenReturn("text");

        boolean result = textHandler.condition(update);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageIsCommand() {
        when(update.message())
            .thenReturn(message);
        when(message.text())
            .thenReturn("/text");

        boolean result = textHandler.condition(update);

        assertThat(result)
            .isFalse();
    }

    @Test
    void testConditionWhenMessageDoesNotHaveText() {
        when(update.message())
            .thenReturn(message);
        when(message.text())
            .thenReturn(null);

        boolean result = textHandler.condition(update);

        assertThat(result)
            .isFalse();
    }

    private AbstractTextHandler textHandler() {
        return new AbstractTextHandler(telegramClient) {
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

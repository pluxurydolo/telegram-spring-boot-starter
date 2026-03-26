package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.telegram.dto.UpdateType.PIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractPicHandlerTests {
    private final AbstractPicHandler picHandler = picHandler();

    @Mock
    private TelegramTextClient telegramTextClient;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private PhotoSize photoSize;

    @Test
    void testUpdateType() {
        UpdateType result = picHandler.updateType();

        assertThat(result)
            .isEqualTo(PIC);
    }

    @Test
    void testCondition() {
        when(update.message())
            .thenReturn(message);
        when(message.photo())
            .thenReturn(new PhotoSize[]{photoSize});

        boolean result = picHandler.condition(update);

        assertThat(result)
            .isTrue();
    }

    @Test
    void testConditionWhenMessageDoesNotContainPic() {
        when(update.message())
            .thenReturn(message);
        when(message.photo())
            .thenReturn(null);

        boolean result = picHandler.condition(update);

        assertThat(result)
            .isFalse();
    }

    private AbstractPicHandler picHandler() {
        return new AbstractPicHandler(telegramTextClient) {

            @Override
            public Mono<String> doWork(Update update) {
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
                return null;
            }
        };
    }
}

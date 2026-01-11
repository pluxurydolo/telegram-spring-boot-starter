package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class AbstractTelegramUpdateHandlerTests {
    private static final AbstractTelegramUpdateHandler UPDATE_HANDLER = updateHandler();

    @Mock
    private Update update;

    @Test
    void testHandle() {
        Mono<String> result = UPDATE_HANDLER.handle(update);

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private static AbstractTelegramUpdateHandler updateHandler() {
        TelegramClient telegramClient = mock(TelegramClient.class);

        return new AbstractTelegramUpdateHandler(telegramClient) {
            @Override
            public boolean condition(Update update) {
                return false;
            }

            @Override
            protected Mono<String> doWork(Update update) {
                return Mono.just("");
            }

            @Override
            protected UpdateType updateType() {
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

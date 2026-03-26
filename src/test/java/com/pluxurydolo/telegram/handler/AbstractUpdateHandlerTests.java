package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class AbstractUpdateHandlerTests {

    @Mock
    private TelegramTextClient telegramTextClient;

    @Mock
    private Update update;

    @Test
    void testHandle() {
        Mono<String> work = Mono.just("Success");

        Mono<String> result = updateHandler(work)
            .handle(update);

        create(result)
            .expectNext("Success")
            .verifyComplete();
    }

    @Test
    void testHandleWhenExceptionOccurred() {
        Mono<String> work = Mono.error(new RuntimeException());
        when(telegramTextClient.sendText(any()))
            .thenReturn(Mono.just(""));

        Mono<String> result = updateHandler(work)
            .handle(update);

        create(result)
            .expectNext("")
            .verifyComplete();
    }

    private AbstractUpdateHandler updateHandler(Mono<String> work) {
        return new AbstractUpdateHandler(telegramTextClient) {

            @Override
            public boolean condition(Update tgUpdate) {
                return false;
            }

            @Override
            protected Mono<String> doWork(Update tgUpdate) {
                return work;
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
            protected Mono<String> onException(Throwable throwable) {
                return Mono.just("");
            }
        };
    }
}

package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.text.AbstractTextHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class TextHandlerExecutorTests {
    private static final AbstractTextHandler TEXT_HANDLER = mock(AbstractTextHandler.class);
    private static final Update UPDATE = mock(Update.class);
    private static final TextHandlerExecutor EXECUTOR = new TextHandlerExecutor(List.of(TEXT_HANDLER));

    @Test
    void testExecute() {
        when(TEXT_HANDLER.condition(any()))
            .thenReturn(true);
        when(TEXT_HANDLER.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenConditionIsFalse() {
        when(TEXT_HANDLER.condition(any()))
            .thenReturn(false);

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenExceptionOccurred() {
        when(TEXT_HANDLER.condition(any()))
            .thenReturn(true);
        when(TEXT_HANDLER.handle(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}

package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.callback.AbstractCallbackHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class CallbackHandlerExecutorTests {
    private static final AbstractCallbackHandler CALLBACK_HANDLER = mock(AbstractCallbackHandler.class);
    private static final Update UPDATE = mock(Update.class);
    private static final CallbackHandlerExecutor EXECUTOR = new CallbackHandlerExecutor(List.of(CALLBACK_HANDLER));

    @Test
    void testExecute() {
        when(CALLBACK_HANDLER.condition(any()))
            .thenReturn(true);
        when(CALLBACK_HANDLER.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenConditionIsFalse() {
        when(CALLBACK_HANDLER.condition(any()))
            .thenReturn(false);

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenExceptionOccurred() {
        when(CALLBACK_HANDLER.condition(any()))
            .thenReturn(true);
        when(CALLBACK_HANDLER.handle(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}

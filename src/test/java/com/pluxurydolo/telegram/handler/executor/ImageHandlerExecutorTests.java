package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.media.AbstractImageHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class ImageHandlerExecutorTests {
    private static final AbstractImageHandler IMAGE_HANDLER = mock(AbstractImageHandler.class);
    private static final Update UPDATE = mock(Update.class);
    private static final ImageHandlerExecutor EXECUTOR = new ImageHandlerExecutor(List.of(IMAGE_HANDLER));

    @Test
    void testExecute() {
        when(IMAGE_HANDLER.condition(any()))
            .thenReturn(true);
        when(IMAGE_HANDLER.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenConditionIsFalse() {
        when(IMAGE_HANDLER.condition(any()))
            .thenReturn(false);

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenExceptionOccurred() {
        when(IMAGE_HANDLER.condition(any()))
            .thenReturn(true);
        when(IMAGE_HANDLER.handle(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}

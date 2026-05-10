package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.media.AbstractVideoHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class VideoHandlerExecutorTests {
    private static final AbstractVideoHandler VIDEO_HANDLER = mock(AbstractVideoHandler.class);
    private static final Update UPDATE = mock(Update.class);
    private static final VideoHandlerExecutor EXECUTOR = new VideoHandlerExecutor(List.of(VIDEO_HANDLER));

    @Test
    void testExecute() {
        when(VIDEO_HANDLER.condition(any()))
            .thenReturn(true);
        when(VIDEO_HANDLER.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenConditionIsFalse() {
        when(VIDEO_HANDLER.condition(any()))
            .thenReturn(false);

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenExceptionOccurred() {
        when(VIDEO_HANDLER.condition(any()))
            .thenReturn(true);
        when(VIDEO_HANDLER.handle(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}

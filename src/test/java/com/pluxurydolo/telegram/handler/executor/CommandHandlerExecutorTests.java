package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.text.AbstractCommandHandler;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

class CommandHandlerExecutorTests {
    private static final AbstractCommandHandler COMMAND_HANDLER = mock(AbstractCommandHandler.class);
    private static final Update UPDATE = mock(Update.class);
    private static final CommandHandlerExecutor EXECUTOR = new CommandHandlerExecutor(List.of(COMMAND_HANDLER));

    @Test
    void testExecute() {
        when(COMMAND_HANDLER.condition(any()))
            .thenReturn(true);
        when(COMMAND_HANDLER.handle(any()))
            .thenReturn(Mono.just(""));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenConditionIsFalse() {
        when(COMMAND_HANDLER.condition(any()))
            .thenReturn(false);

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .expectNext(UPDATE)
            .verifyComplete();
    }

    @Test
    void testExecuteWhenExceptionOccurred() {
        when(COMMAND_HANDLER.condition(any()))
            .thenReturn(true);
        when(COMMAND_HANDLER.handle(any()))
            .thenReturn(Mono.error(new RuntimeException()));

        Mono<Update> result = EXECUTOR.execute(UPDATE);

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(RuntimeException.class));
    }
}

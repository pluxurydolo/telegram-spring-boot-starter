package com.pluxurydolo.telegram.handler.executor;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AbstractHandlerExecutor {
    private final List<? extends AbstractUpdateHandler> handlers;

    protected AbstractHandlerExecutor(List<? extends AbstractUpdateHandler> handlers) {
        this.handlers = handlers;
    }

    public Mono<Update> execute(Update update) {
        return Flux.fromIterable(handlers)
            .filter(commandHandler -> commandHandler.condition(update))
            .next()
            .flatMap(commandHandler -> commandHandler.handle(update))
            .thenReturn(update);
    }
}

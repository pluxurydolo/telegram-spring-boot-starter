
package com.pluxurydolo.telegram.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.handler.executor.AbstractHandlerExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class TelegramUpdatesListener implements UpdatesListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramUpdatesListener.class);

    private final FilterExecutor filterExecutor;
    private final List<AbstractHandlerExecutor> executors;

    public TelegramUpdatesListener(FilterExecutor filterExecutor, List<AbstractHandlerExecutor> executors) {
        this.filterExecutor = filterExecutor;
        this.executors = executors;
    }

    @Override
    public int process(List<Update> updates) {
        Flux.fromIterable(updates)
            .filterWhen(filterExecutor::execute)
            .flatMap(this::handleUpdate)
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();

        return CONFIRMED_UPDATES_ALL;
    }

    private Mono<Update> handleUpdate(Update update) {
        Message message = update.message();
        long senderId = message.from().id();
        String text = message.text();

        LOGGER.info("phyb ID отправителя={} Текст сообщения={}", senderId, text);

        return Flux.fromIterable(executors)
            .flatMap(executor -> executor.execute(update))
            .collectList()
            .thenReturn(update);
    }
}

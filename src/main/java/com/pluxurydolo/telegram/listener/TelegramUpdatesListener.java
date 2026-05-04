package com.pluxurydolo.telegram.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.filter.FilterExecutor;
import com.pluxurydolo.telegram.handler.executor.AbstractHandlerExecutor;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
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
    private final TelegramUpdateParser telegramUpdateParser;

    public TelegramUpdatesListener(
        FilterExecutor filterExecutor,
        List<AbstractHandlerExecutor> executors,
        TelegramUpdateParser telegramUpdateParser
    ) {
        this.filterExecutor = filterExecutor;
        this.executors = executors;
        this.telegramUpdateParser = telegramUpdateParser;
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
        long senderId = telegramUpdateParser.getSenderId(update);
        String text = telegramUpdateParser.getText(update);

        LOGGER.info("phyb [telegram-starter] ID отправителя={} Текст сообщения={}", senderId, text);

        return Flux.fromIterable(executors)
            .flatMap(executor -> executor.execute(update))
            .collectList()
            .thenReturn(update);
    }
}

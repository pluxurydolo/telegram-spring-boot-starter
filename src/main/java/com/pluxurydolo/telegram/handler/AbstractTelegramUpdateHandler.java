package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.client.TelegramClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class AbstractTelegramUpdateHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTelegramUpdateHandler.class);

    private final TelegramClient telegramClient;

    protected AbstractTelegramUpdateHandler(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public Mono<String> handle(Update update) {
        return doWork(update)
            .doOnSuccess(it -> LOGGER.info("zxsf Сообщение типа {} успешно обработано", updateType()))
            .onErrorResume(this::handleException);
    }

    private Mono<String> handleException(Throwable throwable) {
        LOGGER.error("wlrf Произошла ошибка при обработке сообщения типа {}", updateType());

        return onException(throwable)
            .flatMap(it -> telegramClient.sendPlainText(failMessage(), recepientId(), telegramBot()));
    }

    public abstract boolean condition(Update update);

    protected abstract Mono<String> doWork(Update update);

    protected abstract UpdateType updateType();

    protected abstract String failMessage();

    protected abstract TelegramBot telegramBot();

    protected abstract long recepientId();

    protected abstract Mono<String> onException(Throwable throwable);
}

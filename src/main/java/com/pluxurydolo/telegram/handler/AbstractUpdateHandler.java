package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class AbstractUpdateHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateHandler.class);

    private final TelegramTextClient telegramTextClient;

    protected AbstractUpdateHandler(TelegramTextClient telegramTextClient) {
        this.telegramTextClient = telegramTextClient;
    }

    public Mono<String> handle(Update update) {
        return doWork(update)
            .doOnSuccess(_ -> LOGGER.info("zxsf [telegram-starter] Сообщение типа {} успешно обработано", updateType()))
            .onErrorResume(this::handleException);
    }

    private Mono<String> handleException(Throwable throwable) {
        LOGGER.error("wlrf [telegram-starter] Произошла ошибка при обработке сообщения типа {}", updateType());

        String text = failMessage();
        TelegramBot telegramBot = telegramBot();
        SendTextRequest sendTextRequest = new SendTextRequest(text, telegramBot);

        return onException(throwable)
            .flatMap(_ -> telegramTextClient.sendText(sendTextRequest));
    }

    public abstract boolean condition(Update update);

    protected abstract Mono<String> doWork(Update update);

    protected abstract UpdateType updateType();

    protected abstract String failMessage();

    protected abstract TelegramBot telegramBot();

    protected abstract Mono<String> onException(Throwable throwable);
}

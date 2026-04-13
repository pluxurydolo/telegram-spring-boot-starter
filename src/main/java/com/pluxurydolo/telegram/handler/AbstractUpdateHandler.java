package com.pluxurydolo.telegram.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class AbstractUpdateHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateHandler.class);

    private final TelegramTextClient telegramTextClient;
    private final PerUserRateLimiter perUserRateLimiter;

    protected AbstractUpdateHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        this.telegramTextClient = telegramTextClient;
        this.perUserRateLimiter = perUserRateLimiter;
    }

    public Mono<String> handle(Update update) {
        return perUserRateLimiter.withRateLimit(update, doWork(update))
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

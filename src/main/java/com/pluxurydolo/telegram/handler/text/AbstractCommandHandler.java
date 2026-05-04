package com.pluxurydolo.telegram.handler.text;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.COMMAND;

public abstract class AbstractCommandHandler extends AbstractUpdateHandler {
    protected AbstractCommandHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        super(telegramTextClient, perUserRateLimiter);
    }

    @Override
    public UpdateType updateType() {
        return COMMAND;
    }

    @Override
    public boolean condition(Update update) {
        return Optional.ofNullable(update.message())
            .map(Message::text)
            .map(text -> text.startsWith(command()))
            .orElse(false);
    }

    protected abstract String command();
}

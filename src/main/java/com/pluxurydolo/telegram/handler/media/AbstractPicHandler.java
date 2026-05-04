package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.PIC;

public abstract class AbstractPicHandler extends AbstractMediaHandler {
    protected AbstractPicHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        super(telegramTextClient, perUserRateLimiter);
    }

    @Override
    public UpdateType updateType() {
        return PIC;
    }

    @Override
    public boolean condition(Update update) {
        return Optional.ofNullable(update.message())
            .map(message -> message.photo() != null)
            .orElse(false);
    }
}

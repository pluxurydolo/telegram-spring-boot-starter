package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.AUDIO;

public abstract class AbstractAudioHandler extends AbstractUpdateHandler {
    protected AbstractAudioHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        super(telegramTextClient, perUserRateLimiter);
    }

    @Override
    public UpdateType updateType() {
        return AUDIO;
    }

    @Override
    public boolean condition(Update update) {
        return Optional.ofNullable(update.message())
            .map(message -> message.audio() != null)
            .orElse(false);
    }
}

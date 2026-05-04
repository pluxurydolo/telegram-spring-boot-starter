package com.pluxurydolo.telegram.handler.callback;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;

import static com.pluxurydolo.telegram.dto.UpdateType.CALLBACK;

public abstract class AbstractCallbackHandler extends AbstractUpdateHandler {
    protected AbstractCallbackHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        super(telegramTextClient, perUserRateLimiter);
    }

    @Override
    public UpdateType updateType() {
        return CALLBACK;
    }

    @Override
    public boolean condition(Update update) {
        return update.callbackQuery() != null;
    }
}

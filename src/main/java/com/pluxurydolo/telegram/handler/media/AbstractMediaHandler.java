package com.pluxurydolo.telegram.handler.media;

import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;
import com.pluxurydolo.telegram.ratelimiter.PerUserRateLimiter;

public abstract class AbstractMediaHandler extends AbstractUpdateHandler {
    protected AbstractMediaHandler(TelegramTextClient telegramTextClient, PerUserRateLimiter perUserRateLimiter) {
        super(telegramTextClient, perUserRateLimiter);
    }
}

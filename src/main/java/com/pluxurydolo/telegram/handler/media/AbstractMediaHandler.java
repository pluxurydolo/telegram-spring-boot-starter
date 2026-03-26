package com.pluxurydolo.telegram.handler.media;

import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;

public abstract class AbstractMediaHandler extends AbstractUpdateHandler {
    protected AbstractMediaHandler(TelegramTextClient telegramTextClient) {
        super(telegramTextClient);
    }
}

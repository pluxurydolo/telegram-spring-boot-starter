package com.pluxurydolo.telegram.handler.media;

import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.handler.AbstractTelegramUpdateHandler;

public abstract class AbstractMediaHandler extends AbstractTelegramUpdateHandler {
    protected AbstractMediaHandler(TelegramClient telegramClient) {
        super(telegramClient);
    }
}

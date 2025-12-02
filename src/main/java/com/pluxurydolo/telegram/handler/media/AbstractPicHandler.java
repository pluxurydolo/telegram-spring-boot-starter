package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.client.TelegramClient;

import static com.pluxurydolo.telegram.dto.UpdateType.PIC;

public abstract class AbstractPicHandler extends AbstractMediaHandler {
    protected AbstractPicHandler(TelegramClient telegramClient) {
        super(telegramClient);
    }

    @Override
    public UpdateType updateType() {
        return PIC;
    }

    @Override
    public boolean condition(Update update) {
        return update.message().photo() != null;
    }
}

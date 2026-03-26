package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;

import static com.pluxurydolo.telegram.dto.UpdateType.PIC;

public abstract class AbstractPicHandler extends AbstractMediaHandler {
    protected AbstractPicHandler(TelegramTextClient telegramTextClient) {
        super(telegramTextClient);
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

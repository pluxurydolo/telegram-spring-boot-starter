package com.pluxurydolo.telegram.handler.media;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.client.TelegramClient;
import org.springframework.stereotype.Component;

import static com.pluxurydolo.telegram.dto.UpdateType.VIDEO;

@Component
public abstract class AbstractVideoHandler extends AbstractMediaHandler {
    protected AbstractVideoHandler(TelegramClient telegramClient) {
        super(telegramClient);
    }

    @Override
    public UpdateType updateType() {
        return VIDEO;
    }

    @Override
    public boolean condition(Update update) {
        return update.message().video() != null;
    }
}

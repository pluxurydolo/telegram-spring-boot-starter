package com.pluxurydolo.telegram.handler.text;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.handler.AbstractTelegramUpdateHandler;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.LINK;

public abstract class AbstractLinkHandler extends AbstractTelegramUpdateHandler {
    protected AbstractLinkHandler(TelegramClient telegramClient) {
        super(telegramClient);
    }

    @Override
    public boolean condition(Update update) {
        Optional<String> text = Optional.ofNullable(update.message().text());

        return text.map(it -> it.startsWith("https://"))
            .orElse(false);
    }

    @Override
    protected UpdateType updateType() {
        return LINK;
    }
}

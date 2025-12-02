package com.pluxurydolo.telegram.handler.command;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.client.TelegramClient;
import com.pluxurydolo.telegram.handler.AbstractTelegramUpdateHandler;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.COMMAND;

public abstract class AbstractCommandHandler extends AbstractTelegramUpdateHandler {
    protected AbstractCommandHandler(TelegramClient telegramClient) {
        super(telegramClient);
    }

    @Override
    public boolean condition(Update update) {
        Optional<String> text = Optional.ofNullable(update.message().text());

        return text.map(it -> it.startsWith(command()))
            .orElse(false);
    }

    @Override
    public UpdateType updateType() {
        return COMMAND;
    }

    protected abstract String command();
}

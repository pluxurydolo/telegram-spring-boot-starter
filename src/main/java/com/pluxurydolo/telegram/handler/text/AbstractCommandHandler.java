package com.pluxurydolo.telegram.handler.text;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.UpdateType;
import com.pluxurydolo.telegram.handler.AbstractUpdateHandler;

import java.util.Optional;

import static com.pluxurydolo.telegram.dto.UpdateType.COMMAND;

public abstract class AbstractCommandHandler extends AbstractUpdateHandler {
    protected AbstractCommandHandler(TelegramTextClient telegramTextClient) {
        super(telegramTextClient);
    }

    @Override
    public UpdateType updateType() {
        return COMMAND;
    }

    @Override
    public boolean condition(Update update) {
        Optional<String> text = Optional.ofNullable(update.message().text());

        return text.map(it -> it.startsWith(command()))
            .orElse(false);
    }

    protected abstract String command();
}

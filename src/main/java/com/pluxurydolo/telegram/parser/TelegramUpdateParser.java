package com.pluxurydolo.telegram.parser;

import com.pengrad.telegrambot.model.Update;

public class TelegramUpdateParser {
    public long getSenderId(Update update) {
        return update.message()
            .from()
            .id();
    }
}

package com.pluxurydolo.telegram.parser;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public class TelegramUpdateParser {
    public long getSenderId(Update update) {
        Message message = update.message();

        if (message != null) {
            return message.from().id();
        }

        CallbackQuery callbackQuery = update.callbackQuery();

        if (callbackQuery != null) {
            return callbackQuery.from().id();
        }

        return 0;
    }

    public String getText(Update update) {
        Message message = update.message();

        if (message != null) {
            return message.text();
        }

        CallbackQuery callbackQuery = update.callbackQuery();

        if (callbackQuery != null) {
            return callbackQuery.data();
        }

        return "";
    }
}

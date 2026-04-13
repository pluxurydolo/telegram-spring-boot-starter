package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendEditTextRequest(
    String text,
    int messageId,
    TelegramBot bot
) {
}

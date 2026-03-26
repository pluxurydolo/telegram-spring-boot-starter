package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendTextRequest(
    String text,
    TelegramBot bot
) {
}

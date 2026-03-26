package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendTextRequest(
    String text,
    Long userId,
    TelegramBot bot
) {
}

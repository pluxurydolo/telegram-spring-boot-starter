package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendImageRequest(
    byte[] image,
    String caption,
    TelegramBot bot
) {
}

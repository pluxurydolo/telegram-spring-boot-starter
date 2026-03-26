package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendVideoRequest(
    byte[] video,
    String caption,
    Long channelId,
    TelegramBot bot
) {
}

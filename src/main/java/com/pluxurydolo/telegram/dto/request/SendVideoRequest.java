package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record SendVideoRequest(
    byte[] video,
    Long channelId,
    String caption,
    TelegramBot bot
) {
}

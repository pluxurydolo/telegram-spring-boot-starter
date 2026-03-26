package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

public record GetFileRequest(
    String fileId,
    TelegramBot bot
) {
}

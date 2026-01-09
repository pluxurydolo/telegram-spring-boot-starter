package com.pluxurydolo.telegram.dto;

import com.pengrad.telegrambot.TelegramBot;

public record TelegramCommunication(
    TelegramBot telegramBot,
    long recepientId
) {
}

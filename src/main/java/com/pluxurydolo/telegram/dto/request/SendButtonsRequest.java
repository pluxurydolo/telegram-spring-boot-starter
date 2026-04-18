package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;

import java.util.List;

public record SendButtonsRequest(
    String text,
    TelegramBot bot,
    List<ButtonRequest> buttons
) {
}

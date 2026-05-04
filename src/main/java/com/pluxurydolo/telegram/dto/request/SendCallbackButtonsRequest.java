package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;
import com.pluxurydolo.telegram.dto.request.button.CallbackButton;

import java.util.List;

public record SendCallbackButtonsRequest(
    String text,
    TelegramBot bot,
    List<CallbackButton> buttons
) {
}

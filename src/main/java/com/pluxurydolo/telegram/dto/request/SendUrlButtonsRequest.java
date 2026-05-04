package com.pluxurydolo.telegram.dto.request;

import com.pengrad.telegrambot.TelegramBot;
import com.pluxurydolo.telegram.dto.request.button.UrlButton;

import java.util.List;

public record SendUrlButtonsRequest(
    String text,
    TelegramBot bot,
    List<UrlButton> buttons
) {
}

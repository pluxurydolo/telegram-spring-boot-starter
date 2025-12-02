package com.pluxurydolo.telegram.bot;

import com.pengrad.telegrambot.TelegramBot;

public interface TelegramSender {
    TelegramBot telegramBot();

    long getRecepientId();
}

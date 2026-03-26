package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

public class TelegramTextClient {
    private final long userId;

    public TelegramTextClient(long userId) {
        this.userId = userId;
    }

    public Mono<String> sendText(SendTextRequest sendTextRequest) {
        String text = sendTextRequest.text();
        TelegramBot bot = sendTextRequest.bot();

        SendMessage request = new SendMessage(userId, text)
            .parseMode(HTML);

        return Mono.fromCallable(() -> bot.execute(request))
            .thenReturn(text)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

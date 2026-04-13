package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramImageClient {
    private final long channelId;

    public TelegramImageClient(long channelId) {
        this.channelId = channelId;
    }

    public Mono<String> sendImage(SendImageRequest request) {
        byte[] image = request.image();
        String caption = request.caption();
        TelegramBot bot = request.bot();

        SendPhoto sendPhoto = new SendPhoto(channelId, image)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(sendPhoto))
            .thenReturn(caption)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

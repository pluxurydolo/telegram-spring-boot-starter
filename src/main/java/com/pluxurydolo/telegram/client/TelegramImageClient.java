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

    public Mono<String> sendImage(SendImageRequest sendImageRequest) {
        byte[] image = sendImageRequest.image();
        String caption = sendImageRequest.caption();
        TelegramBot bot = sendImageRequest.bot();

        SendPhoto request = new SendPhoto(channelId, image)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(request))
            .thenReturn(caption)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

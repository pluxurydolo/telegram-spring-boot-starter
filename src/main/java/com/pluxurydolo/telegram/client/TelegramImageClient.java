package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramImageClient {
    public Mono<String> sendImage(SendImageRequest sendImageRequest) {
        byte[] image = sendImageRequest.image();
        String caption = sendImageRequest.caption();
        long userId = sendImageRequest.userId();
        TelegramBot bot = sendImageRequest.bot();

        SendPhoto request = new SendPhoto(userId, image)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(request))
            .thenReturn(caption)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

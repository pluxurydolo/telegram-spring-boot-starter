package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendVideo;
import com.pluxurydolo.telegram.dto.request.SendVideoRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramVideoClient {
    private final long channelId;

    public TelegramVideoClient(long channelId) {
        this.channelId = channelId;
    }

    public Mono<String> sendVideo(SendVideoRequest sendVideoRequest) {
        byte[] video = sendVideoRequest.video();
        String caption = sendVideoRequest.caption();
        TelegramBot bot = sendVideoRequest.bot();

        SendVideo request = new SendVideo(channelId, video)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(request))
            .thenReturn(caption)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

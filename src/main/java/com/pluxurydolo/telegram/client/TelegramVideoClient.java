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

    public Mono<String> sendVideo(SendVideoRequest request) {
        byte[] video = request.video();
        String caption = request.caption();
        TelegramBot bot = request.bot();

        SendVideo sendVideo = new SendVideo(channelId, video)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(sendVideo))
            .thenReturn(caption)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

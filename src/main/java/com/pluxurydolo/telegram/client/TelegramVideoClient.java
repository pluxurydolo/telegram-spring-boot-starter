package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendVideo;
import com.pluxurydolo.telegram.dto.request.SendVideoRequest;
import com.pluxurydolo.telegram.exception.SendVideoException;
import com.pluxurydolo.telegram.properties.TelegramChannelProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramVideoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramVideoClient.class);

    private final TelegramChannelProperties telegramChannelProperties;

    public TelegramVideoClient(TelegramChannelProperties telegramChannelProperties) {
        this.telegramChannelProperties = telegramChannelProperties;
    }

    public Mono<String> sendVideo(SendVideoRequest request) {
        byte[] video = request.video();
        String caption = request.caption();
        TelegramBot bot = request.bot();

        long channelId = telegramChannelProperties.id();

        SendVideo sendVideo = new SendVideo(channelId, video)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(sendVideo))
            .thenReturn(caption)
            .onErrorResume(throwable -> {
                LOGGER.error("zztg [telegram-starter] Произошла ошибка при отправке видео");
                return Mono.error(new SendVideoException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}

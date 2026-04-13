package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
import com.pluxurydolo.telegram.exception.SendImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramImageClient.class);

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
            .onErrorResume(throwable -> {
                LOGGER.error("mlap [telegram-starter] Произошла ошибка при отправке картинки");
                return Mono.error(new SendImageException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}

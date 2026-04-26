package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
import com.pluxurydolo.telegram.exception.SendImageException;
import com.pluxurydolo.telegram.properties.TelegramChannelProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramImageClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramImageClient.class);

    private final TelegramChannelProperties telegramChannelProperties;

    public TelegramImageClient(TelegramChannelProperties telegramChannelProperties) {
        this.telegramChannelProperties = telegramChannelProperties;
    }

    public Mono<String> sendImage(SendImageRequest request) {
        byte[] image = request.image();
        String caption = request.caption();
        TelegramBot bot = request.bot();

        long channelId = telegramChannelProperties.id();

        SendPhoto sendPhoto = new SendPhoto(channelId, image)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(sendPhoto))
            .thenReturn(caption)
            .doOnSuccess(_ -> LOGGER.info("uhue [telegram-starter] Картинка с подписью {} успешно отправлена", caption))
            .onErrorResume(throwable -> {
                LOGGER.error("mlap [telegram-starter] Произошла ошибка при отправке картинки с подписью {}", caption);
                return Mono.error(new SendImageException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}

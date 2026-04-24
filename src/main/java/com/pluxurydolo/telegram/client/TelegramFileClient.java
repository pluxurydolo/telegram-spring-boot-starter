package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetFile;
import com.pluxurydolo.telegram.dto.request.GetFileRequest;
import com.pluxurydolo.telegram.exception.GetFileException;
import com.pluxurydolo.telegram.properties.TelegramApiProperties;
import com.pluxurydolo.telegram.util.MediaRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramFileClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramFileClient.class);

    private final TelegramApiProperties telegramApiProperties;
    private final MediaRetriever mediaRetriever;

    public TelegramFileClient(TelegramApiProperties telegramApiProperties, MediaRetriever mediaRetriever) {
        this.telegramApiProperties = telegramApiProperties;
        this.mediaRetriever = mediaRetriever;
    }

    public Mono<byte[]> getFile(GetFileRequest request) {
        String fileId = request.fileId();
        TelegramBot bot = request.bot();

        GetFile getFile = new GetFile(fileId);
        String fileUri = telegramApiProperties.fileUri();

        return Mono.fromCallable(() -> bot.execute(getFile))
            .map(response -> response.file().filePath())
            .map(filePath -> String.format(fileUri, filePath))
            .flatMap(mediaRetriever::retrieve)
            .onErrorResume(throwable -> {
                LOGGER.error("ktmh [telegram-starter] Произошла ошибка при получении файла {}", fileId);
                return Mono.error(new GetFileException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}

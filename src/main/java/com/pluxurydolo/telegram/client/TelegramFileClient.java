package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetFile;
import com.pluxurydolo.telegram.dto.request.GetFileRequest;
import com.pluxurydolo.telegram.util.MediaRetriever;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramFileClient {
    private final MediaRetriever mediaRetriever;
    private final String fileUri;

    public TelegramFileClient(MediaRetriever mediaRetriever, String fileUri) {
        this.mediaRetriever = mediaRetriever;
        this.fileUri = fileUri;
    }

    public Mono<byte[]> getFile(GetFileRequest request) {
        String fileId = request.fileId();
        TelegramBot bot = request.bot();

        GetFile getFile = new GetFile(fileId);

        return Mono.fromCallable(() -> bot.execute(getFile))
            .map(response -> response.file().filePath())
            .map(filePath -> String.format(fileUri, filePath))
            .flatMap(mediaRetriever::retrieve)
            .subscribeOn(Schedulers.boundedElastic());
    }
}

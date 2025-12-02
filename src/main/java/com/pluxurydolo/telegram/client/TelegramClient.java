package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendVideo;
import com.pengrad.telegrambot.response.GetFileResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

public class TelegramClient {
    public Mono<String> sendPlainText(String text, long userId, TelegramBot bot) {
        SendMessage request = new SendMessage(userId, text)
            .parseMode(HTML);

        return Mono.fromCallable(() -> bot.execute(request))
            .publishOn(Schedulers.boundedElastic())
            .thenReturn(String.valueOf(userId));
    }

    public Mono<String> sendImage(byte[] image, long userId, String caption, TelegramBot bot) {
        SendPhoto request = new SendPhoto(userId, image)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(request))
            .publishOn(Schedulers.boundedElastic())
            .thenReturn(String.valueOf(userId));
    }

    public Mono<String> sendVideo(byte[] video, long channelId, String caption, TelegramBot bot) {
        SendVideo request = new SendVideo(channelId, video)
            .caption(caption);

        return Mono.fromCallable(() -> bot.execute(request))
            .publishOn(Schedulers.boundedElastic())
            .thenReturn(String.valueOf(channelId));
    }

    public Mono<GetFileResponse> getFile(String fileId, TelegramBot bot) {
        GetFile request = new GetFile(fileId);

        return Mono.fromCallable(() -> bot.execute(request))
            .publishOn(Schedulers.boundedElastic());
    }
}

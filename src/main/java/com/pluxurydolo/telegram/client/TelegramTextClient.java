package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pluxurydolo.telegram.dto.request.SendEditTextRequest;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import com.pluxurydolo.telegram.exception.SendEditTextException;
import com.pluxurydolo.telegram.exception.SendTextException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.pengrad.telegrambot.model.request.ParseMode.HTML;

public class TelegramTextClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramTextClient.class);

    private final long userId;

    public TelegramTextClient(long userId) {
        this.userId = userId;
    }

    public Mono<Integer> sendText(SendTextRequest request) {
        String text = request.text();
        TelegramBot bot = request.bot();

        SendMessage sendMessage = new SendMessage(userId, text)
            .parseMode(HTML);

        return Mono.fromCallable(() -> bot.execute(sendMessage))
            .map(response -> response.message().messageId())
            .onErrorResume(throwable -> {
                LOGGER.error("rghu [telegram-starter] Произошла ошибка при редактировании сообщения с текстом {}", text);
                return Mono.error(new SendTextException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> sendEditText(SendEditTextRequest request) {
        String text = request.text();
        int messageId = request.messageId();
        TelegramBot bot = request.bot();

        EditMessageText editMessageText = new EditMessageText(userId, messageId, text)
            .parseMode(HTML);

        return Mono.fromCallable(() -> bot.execute(editMessageText))
            .thenReturn(text)
            .onErrorResume(throwable -> {
                LOGGER.error("okhw [telegram-starter] Произошла ошибка при редактировании сообщения с текстом {}", text);
                return Mono.error(new SendEditTextException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}

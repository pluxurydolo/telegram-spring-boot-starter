package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pluxurydolo.telegram.dto.request.ButtonRequest;
import com.pluxurydolo.telegram.dto.request.SendButtonsRequest;
import com.pluxurydolo.telegram.exception.handler.SendButtonsException;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class TelegramButtonClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramButtonClient.class);

    private final TelegramFilterProperties telegramFilterProperties;

    public TelegramButtonClient(TelegramFilterProperties telegramFilterProperties) {
        this.telegramFilterProperties = telegramFilterProperties;
    }

    public Mono<String> sendButtons(SendButtonsRequest request) {
        String text = request.text();
        TelegramBot bot = request.bot();

        InlineKeyboardButton[][] buttons = request.buttons()
            .stream()
            .map(TelegramButtonClient::buildButton)
            .map(button -> new InlineKeyboardButton[]{button})
            .toArray(InlineKeyboardButton[][]::new);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

        long whitelistUserId = telegramFilterProperties.whitelistUserId();

        SendMessage sendMessage = new SendMessage(whitelistUserId, text)
            .replyMarkup(keyboard);

        return Mono.fromCallable(() -> bot.execute(sendMessage))
            .thenReturn(text)
            .onErrorResume(throwable -> {
                LOGGER.error("eexp [telegram-starter] Произошла ошибка при отправке кнопок с текстом {}", text);
                return Mono.error(new SendButtonsException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private static InlineKeyboardButton buildButton(ButtonRequest request) {
        String text = request.text();
        String url = request.url();

        return new InlineKeyboardButton(text)
            .url(url);
    }
}

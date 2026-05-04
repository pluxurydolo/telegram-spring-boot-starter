package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pluxurydolo.telegram.dto.request.SendCallbackButtonsRequest;
import com.pluxurydolo.telegram.dto.request.button.CallbackButton;
import com.pluxurydolo.telegram.dto.request.button.UrlButton;
import com.pluxurydolo.telegram.dto.request.SendUrlButtonsRequest;
import com.pluxurydolo.telegram.exception.SendCallbackButtonsException;
import com.pluxurydolo.telegram.exception.SendUrlButtonsException;
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

    public Mono<String> sendUrlButtons(SendUrlButtonsRequest request) {
        String text = request.text();
        TelegramBot bot = request.bot();

        InlineKeyboardButton[][] buttons = request.buttons()
            .stream()
            .map(TelegramButtonClient::buildUrlButton)
            .map(button -> new InlineKeyboardButton[]{button})
            .toArray(InlineKeyboardButton[][]::new);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

        long whitelistUserId = telegramFilterProperties.whitelistUserId();

        SendMessage sendMessage = new SendMessage(whitelistUserId, text)
            .replyMarkup(keyboard);

        return Mono.fromCallable(() -> bot.execute(sendMessage))
            .thenReturn(text)
            .doOnSuccess(_ -> LOGGER.info("hyoi [telegram-starter] URL-кнопки с текстом {} успешно отправлены", text))
            .onErrorResume(throwable -> {
                LOGGER.error("eexp [telegram-starter] Произошла ошибка при отправке URL-кнопок с текстом {}", text);
                return Mono.error(new SendUrlButtonsException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> sendCallbackButtons(SendCallbackButtonsRequest request) {
        String text = request.text();
        TelegramBot bot = request.bot();

        InlineKeyboardButton[][] buttons = request.buttons()
            .stream()
            .map(TelegramButtonClient::buildCallbackButton)
            .map(button -> new InlineKeyboardButton[]{button})
            .toArray(InlineKeyboardButton[][]::new);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(buttons);

        long whitelistUserId = telegramFilterProperties.whitelistUserId();

        SendMessage sendMessage = new SendMessage(whitelistUserId, text)
            .replyMarkup(keyboard);

        return Mono.fromCallable(() -> bot.execute(sendMessage))
            .thenReturn(text)
            .doOnSuccess(_ -> LOGGER.info("hecm [telegram-starter] Callback-кнопки с текстом {} успешно отправлены", text))
            .onErrorResume(throwable -> {
                LOGGER.error("bbbv [telegram-starter] Произошла ошибка при отправке callback-кнопок с текстом {}", text);
                return Mono.error(new SendCallbackButtonsException(throwable));
            })
            .subscribeOn(Schedulers.boundedElastic());
    }

    private static InlineKeyboardButton buildUrlButton(UrlButton request) {
        String text = request.text();
        String url = request.url();

        return new InlineKeyboardButton(text)
            .url(url);
    }

    private static InlineKeyboardButton buildCallbackButton(CallbackButton request) {
        String text = request.text();
        String callbackData = request.callbackData();

        return new InlineKeyboardButton(text)
            .callbackData(callbackData);
    }
}

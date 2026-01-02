package com.pluxurydolo.telegram.exception;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelegramExceptionHandler implements ExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramExceptionHandler.class);

    @Override
    public void onException(TelegramException exception) {
        BaseResponse baseResponse = exception.response();

        if (baseResponse != null) {
            int errorCode = baseResponse.errorCode();
            String description = baseResponse.description();
            LOGGER.error("uyzg Возникло исключение Telegram API: {} {}", errorCode, description);
        } else {
            LOGGER.error("idyu Возникло неизвестное исключение Telegram API");
        }
    }
}

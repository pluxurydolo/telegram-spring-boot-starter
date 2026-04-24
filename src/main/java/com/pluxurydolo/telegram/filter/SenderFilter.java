package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.Result;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.pluxurydolo.telegram.dto.Result.fromBoolean;

public class SenderFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderFilter.class);

    private final TelegramFilterProperties telegramFilterProperties;
    private final TelegramUpdateParser telegramUpdateParser;

    public SenderFilter(TelegramFilterProperties telegramFilterProperties, TelegramUpdateParser telegramUpdateParser) {
        this.telegramFilterProperties = telegramFilterProperties;
        this.telegramUpdateParser = telegramUpdateParser;
    }

    @Override
    public Result doFilter(Update update) {
        long whitelistUserId = telegramFilterProperties.whitelistUserId();
        long senderId = telegramUpdateParser.getSenderId(update);
        boolean result = senderId == whitelistUserId;

        LOGGER.info(
            """
                mknh [telegram-starter] Telegram ID отправителя={}
                Разрешенный Telegram ID={}
                Результат валидации={}""",
            senderId, whitelistUserId, result
        );


        if (!result) {
            LOGGER.warn("wwpe [telegram-starter] Запрос не будет обработан, так как пришел от запрещенного Telegram ID");
        }

        return fromBoolean(result);
    }
}

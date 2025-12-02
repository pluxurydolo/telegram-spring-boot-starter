package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.parser.TelegramUpdateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SenderFilter.class);

    private final TelegramUpdateParser telegramUpdateParser;
    private final long allowedUserId;

    public SenderFilter(TelegramUpdateParser telegramUpdateParser, long allowedUserId) {
        this.telegramUpdateParser = telegramUpdateParser;
        this.allowedUserId = allowedUserId;
    }

    @Override
    public boolean doFilter(Update update) {
        long senderId = telegramUpdateParser.getSenderId(update);
        LOGGER.info("mknh Telegram ID отправителя={}\nРазрешенный Telegram ID={}", senderId, allowedUserId);

        boolean result = senderId == allowedUserId;

        if (!result) {
            LOGGER.warn("wwpe Запрос не будет обработан, так как пришел от запрещенного Telegram ID");
        }

        return result;
    }
}

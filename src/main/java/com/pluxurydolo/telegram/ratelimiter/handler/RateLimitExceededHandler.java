package com.pluxurydolo.telegram.ratelimiter.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.dto.request.SendEditTextRequest;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import com.pluxurydolo.telegram.ratelimiter.message.RateLimitExceededMessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class RateLimitExceededHandler {
    private final TelegramTextClient telegramTextClient;
    private final TelegramRateLimitProperties telegramRateLimitProperties;
    private final RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder;

    public RateLimitExceededHandler(
        TelegramTextClient telegramTextClient,
        TelegramRateLimitProperties telegramRateLimitProperties,
        RateLimitExceededMessageBuilder rateLimitExceededMessageBuilder
    ) {
        this.telegramTextClient = telegramTextClient;
        this.telegramRateLimitProperties = telegramRateLimitProperties;
        this.rateLimitExceededMessageBuilder = rateLimitExceededMessageBuilder;
    }

    public Mono<String> handle(TelegramBot telegramBot) {
        long cooldown = telegramRateLimitProperties.refreshPeriod().getSeconds();
        String text = rateLimitExceededMessageBuilder.build(cooldown);
        SendTextRequest sendTextRequest = new SendTextRequest(text, telegramBot);

        return telegramTextClient.sendText(sendTextRequest)
            .flatMap(messageId -> startCountdown(telegramBot, messageId, cooldown));
    }

    private Mono<String> startCountdown(TelegramBot telegramBot, Integer messageId, long cooldown) {
        return Flux.interval(Duration.ofSeconds(2))
            .take(cooldown / 2)
            .map(tick -> cooldown - (tick * 2) - 2)
            .concatMap(secondsLeft -> updateMessage(telegramBot, messageId, secondsLeft))
            .last();
    }

    private Mono<String> updateMessage(TelegramBot telegramBot, Integer messageId, long cooldown) {
        String text = rateLimitExceededMessageBuilder.build(cooldown);
        SendEditTextRequest request = new SendEditTextRequest(text, messageId, telegramBot);
        return telegramTextClient.sendEditText(request);
    }
}

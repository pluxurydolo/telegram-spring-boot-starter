package com.pluxurydolo.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

import java.time.Duration;

@ConfigurationProperties(prefix = "telegram.rate-limit")
public record TelegramRateLimitProperties(
    int threshold,

    @Name("refresh.period")
    Duration refreshPeriod
) {
}

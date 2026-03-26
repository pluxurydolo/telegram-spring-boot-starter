package com.pluxurydolo.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram")
public record TelegramProperties(
    long allowedUserId,
    long channelId,
    String fileUri
) {
}

package com.pluxurydolo.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "telegram")
public record TelegramProperties(

    @Name("filter.sender.id")
    long allowedUserId,

    @Name("channel.id")
    long channelId,

    @Name("file.uri")
    String fileUri
) {
}

package com.pluxurydolo.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "telegram.filter")
public record TelegramFilterProperties(

    @Name("by-user-id.whitelist.user-id")
    long whitelistUserId
) {
}

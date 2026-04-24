package com.pluxurydolo.telegram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "telegram.api")
public record TelegramApiProperties(

    @Name("file-uri")
    String fileUri
) {
}

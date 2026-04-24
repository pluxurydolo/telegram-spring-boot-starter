package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.properties.TelegramApiProperties;
import com.pluxurydolo.telegram.properties.TelegramChannelProperties;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    TelegramApiProperties.class,
    TelegramFilterProperties.class,
    TelegramChannelProperties.class,
    TelegramRateLimitProperties.class
})
@Import({
    TelegramClientConfiguration.class,
    TelegramUpdateConfiguration.class,
    TelegramFilterConfiguration.class,
    TelegramExceptionConfiguration.class,
    TelegramRateLimitConfiguration.class
})
public class TelegramAutoConfiguration {
}

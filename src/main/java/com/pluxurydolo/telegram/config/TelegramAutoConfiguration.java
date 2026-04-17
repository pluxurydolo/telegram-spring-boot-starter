package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.properties.TelegramProperties;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    TelegramProperties.class,
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

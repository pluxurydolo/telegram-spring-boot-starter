package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.properties.TelegramProperties;
import com.pluxurydolo.telegram.properties.TelegramRateLimitProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "telegram", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({
    TelegramProperties.class,
    TelegramRateLimitProperties.class
})
@Import({
    TelegramClientConfiguration.class,
    TelegramUpdateConfiguration.class,
    TelegramFilterConfiguration.class,
    TelegramExceptionConfiguration.class
})
public class TelegramAutoConfiguration {
}

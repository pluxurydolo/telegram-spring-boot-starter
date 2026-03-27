package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.properties.TelegramProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "telegram", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(TelegramProperties.class)
@Import({
    TelegramClientConfiguration.class,
    TelegramUpdateConfiguration.class,
    TelegramFilterConfiguration.class,
    TelegramExceptionConfiguration.class
})
public class TelegramAutoConfiguration {
}

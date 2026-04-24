package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.client.TelegramButtonClient;
import com.pluxurydolo.telegram.client.TelegramFileClient;
import com.pluxurydolo.telegram.client.TelegramImageClient;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.client.TelegramVideoClient;
import com.pluxurydolo.telegram.properties.TelegramApiProperties;
import com.pluxurydolo.telegram.properties.TelegramChannelProperties;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import com.pluxurydolo.telegram.util.MediaRetriever;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TelegramTextClient telegramTextClient(TelegramFilterProperties telegramFilterProperties) {
        return new TelegramTextClient(telegramFilterProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramImageClient telegramImageClient(TelegramChannelProperties telegramChannelProperties) {
        return new TelegramImageClient(telegramChannelProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramVideoClient telegramVideoClient(TelegramChannelProperties telegramChannelProperties) {
        return new TelegramVideoClient(telegramChannelProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramFileClient telegramFileClient(
        TelegramApiProperties telegramApiProperties,
        MediaRetriever mediaRetriever
    ) {
        return new TelegramFileClient(telegramApiProperties, mediaRetriever);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramButtonClient telegramButtonClient(TelegramFilterProperties telegramFilterProperties) {
        return new TelegramButtonClient(telegramFilterProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MediaRetriever mediaRetriever() {
        return new MediaRetriever();
    }
}

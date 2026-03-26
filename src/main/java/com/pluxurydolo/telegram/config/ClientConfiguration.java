package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.client.TelegramFileClient;
import com.pluxurydolo.telegram.client.TelegramImageClient;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.client.TelegramVideoClient;
import com.pluxurydolo.telegram.properties.TelegramProperties;
import com.pluxurydolo.telegram.util.MediaRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public TelegramTextClient telegramTextClient(TelegramProperties telegramProperties) {
        long userId = telegramProperties.allowedUserId();
        return new TelegramTextClient(userId);
    }

    @Bean
    public TelegramImageClient telegramImageClient() {
        return new TelegramImageClient();
    }

    @Bean
    public TelegramVideoClient telegramVideoClient() {
        return new TelegramVideoClient();
    }

    @Bean
    public TelegramFileClient telegramFileClient(MediaRetriever mediaRetriever, TelegramProperties telegramProperties) {
        String fileUri = telegramProperties.fileUri();
        return new TelegramFileClient(mediaRetriever, fileUri);
    }

    @Bean
    public MediaRetriever mediaRetriever() {
        return new MediaRetriever();
    }
}

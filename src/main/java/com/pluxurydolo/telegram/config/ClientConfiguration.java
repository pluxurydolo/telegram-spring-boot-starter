package com.pluxurydolo.telegram.config;

import com.pluxurydolo.telegram.client.TelegramFileClient;
import com.pluxurydolo.telegram.client.TelegramImageClient;
import com.pluxurydolo.telegram.client.TelegramTextClient;
import com.pluxurydolo.telegram.client.TelegramVideoClient;
import com.pluxurydolo.telegram.util.MediaRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    private final String fileUri;

    public ClientConfiguration(@Value("${telegram.file-uri}") String fileUri) {
        this.fileUri = fileUri;
    }

    @Bean
    public TelegramTextClient telegramTextClient() {
        return new TelegramTextClient();
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
    public TelegramFileClient telegramFileClient(MediaRetriever mediaRetriever) {
        return new TelegramFileClient(mediaRetriever, fileUri);
    }

    @Bean
    public MediaRetriever mediaRetriever() {
        return new MediaRetriever();
    }
}

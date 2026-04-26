package com.pluxurydolo.telegram.configuration;

import com.pluxurydolo.telegram.util.MediaRetriever;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class MediaRetrieverTestConfiguration {

    @Bean
    public MediaRetriever mediaRetriever() {
        MediaRetriever mock = mock(MediaRetriever.class);
        byte[] bytes = {};

        when(mock.retrieve(anyString()))
            .thenReturn(Mono.just(bytes));

        return mock;
    }
}

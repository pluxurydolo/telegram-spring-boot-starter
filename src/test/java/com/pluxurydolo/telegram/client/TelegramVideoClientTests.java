package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendVideoRequest;
import com.pluxurydolo.telegram.exception.SendVideoException;
import com.pluxurydolo.telegram.properties.TelegramChannelProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramVideoClientTests {

    @Mock
    private TelegramChannelProperties telegramChannelProperties;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @InjectMocks
    private TelegramVideoClient telegramVideoClient;

    @BeforeEach
    void setUp() {
        when(telegramChannelProperties.id())
            .thenReturn(123L);
    }

    @Test
    void testSendVideo() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = telegramVideoClient.sendVideo(sendVideoRequest(telegramBot));

        create(result)
            .expectNext("caption")
            .verifyComplete();
    }

    @Test
    void testSendVideoWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = telegramVideoClient.sendVideo(sendVideoRequest(telegramBot));

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(SendVideoException.class));
    }

    private static SendVideoRequest sendVideoRequest(TelegramBot telegramBot) {
        byte[] video = {};
        String caption = "caption";
        return new SendVideoRequest(video, caption, telegramBot);
    }
}

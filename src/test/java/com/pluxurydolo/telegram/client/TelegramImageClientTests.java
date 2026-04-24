package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
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
class TelegramImageClientTests {

    @Mock
    private TelegramChannelProperties telegramChannelProperties;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @InjectMocks
    private TelegramImageClient telegramImageClient;

    @BeforeEach
    void setUp() {
        when(telegramChannelProperties.id())
            .thenReturn(123L);
    }

    @Test
    void testSendImage() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = telegramImageClient.sendImage(sendImageRequest(telegramBot));

        create(result)
            .expectNext("caption")
            .verifyComplete();
    }

    @Test
    void testSendImageWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = telegramImageClient.sendImage(sendImageRequest(telegramBot));

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static SendImageRequest sendImageRequest(TelegramBot telegramBot) {
        byte[] image = {};
        String caption = "caption";
        return new SendImageRequest(image, caption, telegramBot);
    }
}

package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendVideoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramVideoClientTests {
    private static final TelegramVideoClient CLIENT = new TelegramVideoClient(1L);

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @Test
    void testSendVideo() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = CLIENT.sendVideo(sendVideoRequest(telegramBot));

        create(result)
            .expectNext("caption")
            .verifyComplete();
    }

    @Test
    void testSendVideoWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = CLIENT.sendVideo(sendVideoRequest(telegramBot));

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static SendVideoRequest sendVideoRequest(TelegramBot telegramBot) {
        byte[] video = {};
        String caption = "caption";
        return new SendVideoRequest(video, caption, telegramBot);
    }
}

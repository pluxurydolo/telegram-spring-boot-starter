package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendImageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramImageClientTests {
    private static final TelegramImageClient CLIENT = new TelegramImageClient();

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @Test
    void testSendImage() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = CLIENT.sendImage(sendImageRequest(telegramBot));

        create(result)
            .expectNext("caption")
            .verifyComplete();
    }

    private static SendImageRequest sendImageRequest(TelegramBot telegramBot) {
        byte[] image = {};
        String caption = "caption";
        long userId = 1L;
        return new SendImageRequest(image, caption, userId, telegramBot);
    }
}

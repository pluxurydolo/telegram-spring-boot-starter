package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramTextClientTests {
    private static final TelegramTextClient CLIENT = new TelegramTextClient(123L);

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @Test
    void testSendText() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = CLIENT.sendText(sendTextRequest(telegramBot));

        create(result)
            .expectNext("123")
            .verifyComplete();
    }

    private static SendTextRequest sendTextRequest(TelegramBot telegramBot) {
        String text = "text";
        return new SendTextRequest(text, telegramBot);
    }
}

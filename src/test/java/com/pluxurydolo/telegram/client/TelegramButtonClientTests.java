package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.ButtonRequest;
import com.pluxurydolo.telegram.dto.request.SendButtonsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramButtonClientTests {
    private static final TelegramButtonClient CLIENT = new TelegramButtonClient(123L);

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @Test
    void testSendButtons() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = CLIENT.sendButtons(sendButtonsRequest(telegramBot));

        create(result)
            .expectNext("text")
            .verifyComplete();
    }

    @Test
    void testSendButtonsWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = CLIENT.sendButtons(sendButtonsRequest(telegramBot));

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static SendButtonsRequest sendButtonsRequest(TelegramBot telegramBot) {
        ButtonRequest buttonRequest = new ButtonRequest("text", "url");
        List<ButtonRequest> buttons = List.of(buttonRequest);
        return new SendButtonsRequest("text", telegramBot, buttons);
    }
}

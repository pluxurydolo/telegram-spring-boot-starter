package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.ButtonRequest;
import com.pluxurydolo.telegram.dto.request.SendButtonsRequest;
import com.pluxurydolo.telegram.properties.TelegramFilterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @Mock
    private TelegramFilterProperties telegramFilterProperties;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @InjectMocks
    private TelegramButtonClient telegramButtonClient;

    @BeforeEach
    void setUp() {
        when(telegramFilterProperties.whitelistUserId())
            .thenReturn(123L);
    }

    @Test
    void testSendButtons() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = telegramButtonClient.sendButtons(sendButtonsRequest(telegramBot));

        create(result)
            .expectNext("text")
            .verifyComplete();
    }

    @Test
    void testSendButtonsWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = telegramButtonClient.sendButtons(sendButtonsRequest(telegramBot));

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

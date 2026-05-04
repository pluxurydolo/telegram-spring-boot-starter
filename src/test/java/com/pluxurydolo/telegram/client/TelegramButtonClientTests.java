package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendCallbackButtonsRequest;
import com.pluxurydolo.telegram.dto.request.SendUrlButtonsRequest;
import com.pluxurydolo.telegram.dto.request.button.CallbackButton;
import com.pluxurydolo.telegram.dto.request.button.UrlButton;
import com.pluxurydolo.telegram.exception.SendCallbackButtonsException;
import com.pluxurydolo.telegram.exception.SendUrlButtonsException;
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
    void testSendUrlButtons() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = telegramButtonClient.sendUrlButtons(sendUrlButtonsRequest(telegramBot));

        create(result)
            .expectNext("text")
            .verifyComplete();
    }

    @Test
    void testSendUrlButtonsWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = telegramButtonClient.sendUrlButtons(sendUrlButtonsRequest(telegramBot));

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(SendUrlButtonsException.class));
    }

    @Test
    void testSendCallbackButtons() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = telegramButtonClient.sendCallbackButtons(sendCallbackButtonsRequest(telegramBot));

        create(result)
            .expectNext("text")
            .verifyComplete();
    }

    @Test
    void testSendCallbackButtonsWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = telegramButtonClient.sendCallbackButtons(sendCallbackButtonsRequest(telegramBot));

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(SendCallbackButtonsException.class));
    }

    private static SendUrlButtonsRequest sendUrlButtonsRequest(TelegramBot telegramBot) {
        UrlButton urlButton = new UrlButton("text", "url");
        List<UrlButton> buttons = List.of(urlButton);
        return new SendUrlButtonsRequest("text", telegramBot, buttons);
    }

    private static SendCallbackButtonsRequest sendCallbackButtonsRequest(TelegramBot telegramBot) {
        CallbackButton callbackButton = new CallbackButton("text", "callbackData");
        List<CallbackButton> buttons = List.of(callbackButton);
        return new SendCallbackButtonsRequest("text", telegramBot, buttons);
    }
}

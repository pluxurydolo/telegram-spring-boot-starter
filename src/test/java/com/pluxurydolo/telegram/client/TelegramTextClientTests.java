package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.dto.request.SendEditTextRequest;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
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
class TelegramTextClientTests {
    private static final TelegramTextClient CLIENT = new TelegramTextClient(123L);

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private SendResponse sendResponse;

    @Mock
    private Message message;

    @Test
    void testSendText() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);
        when(sendResponse.message())
            .thenReturn(message);
        when(message.messageId())
            .thenReturn(1);

        Mono<Integer> result = CLIENT.sendText(sendTextRequest(telegramBot));

        create(result)
            .expectNext(1)
            .verifyComplete();
    }

    @Test
    void testSendTextWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<Integer> result = CLIENT.sendText(sendTextRequest(telegramBot));

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    @Test
    void testSendEditText() {
        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        Mono<String> result = CLIENT.sendEditText(sendEditTextRequest(telegramBot));

        create(result)
            .expectNext("text")
            .verifyComplete();
    }

    @Test
    void testSendEditTextWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<String> result = CLIENT.sendEditText(sendEditTextRequest(telegramBot));

        create(result)
            .expectError(RuntimeException.class)
            .verify();
    }

    private static SendTextRequest sendTextRequest(TelegramBot telegramBot) {
        return new SendTextRequest("text", telegramBot);
    }

    private static SendEditTextRequest sendEditTextRequest(TelegramBot telegramBot) {
        return new SendEditTextRequest("text", 1, telegramBot);
    }
}

package com.pluxurydolo.telegram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.base.AbstractClientIntegrationTests;
import com.pluxurydolo.telegram.dto.request.SendCallbackButtonsRequest;
import com.pluxurydolo.telegram.dto.request.SendUrlButtonsRequest;
import com.pluxurydolo.telegram.dto.request.button.CallbackButton;
import com.pluxurydolo.telegram.dto.request.button.UrlButton;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

class TelegramButtonClientIntegrationTests extends AbstractClientIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(TelegramButtonClient.class);

    @Autowired
    private TelegramButtonClient telegramButtonClient;

    @Test
    void testSendUrlButtons() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramButtonClient.sendUrlButtons(sendUrlButtonsRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("hyoi [telegram-starter] URL-кнопки с текстом text успешно отправлены");
            });
    }

    @Test
    void testSendCallbackButtons() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramButtonClient.sendCallbackButtons(sendCallbackButtonsRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("hecm [telegram-starter] Callback-кнопки с текстом text успешно отправлены");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static SendUrlButtonsRequest sendUrlButtonsRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        return new SendUrlButtonsRequest("text", telegramBot, urlButtons());
    }

    private static SendCallbackButtonsRequest sendCallbackButtonsRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        return new SendCallbackButtonsRequest("text", telegramBot, callbackButtons());
    }

    private static List<UrlButton> urlButtons() {
        UrlButton urlButton = new UrlButton("text", "url");
        return List.of(urlButton);
    }

    private static List<CallbackButton> callbackButtons() {
        CallbackButton callbackButton = new CallbackButton("text", "callbackData");
        return List.of(callbackButton);
    }
}

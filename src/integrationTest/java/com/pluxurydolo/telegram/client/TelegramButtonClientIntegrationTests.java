package com.pluxurydolo.telegram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.base.AbstractTelegramClientTests;
import com.pluxurydolo.telegram.dto.request.ButtonRequest;
import com.pluxurydolo.telegram.dto.request.SendButtonsRequest;
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

class TelegramButtonClientIntegrationTests extends AbstractTelegramClientTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(TelegramButtonClient.class);

    @Autowired
    private TelegramButtonClient telegramButtonClient;

    @Test
    void testSendButtons() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramButtonClient.sendButtons(sendButtonsRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("hyoi [telegram-starter] Кнопки с текстом text успешно отправлены");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static SendButtonsRequest sendButtonsRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        return new SendButtonsRequest("text", telegramBot, buttons());
    }

    private static List<ButtonRequest> buttons() {
        ButtonRequest buttonRequest = new ButtonRequest("text", "url");
        return List.of(buttonRequest);
    }
}

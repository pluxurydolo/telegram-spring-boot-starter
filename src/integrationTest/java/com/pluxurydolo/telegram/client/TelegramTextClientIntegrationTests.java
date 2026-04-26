package com.pluxurydolo.telegram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.base.AbstractClientIntegrationTests;
import com.pluxurydolo.telegram.dto.request.SendEditTextRequest;
import com.pluxurydolo.telegram.dto.request.SendTextRequest;
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

class TelegramTextClientIntegrationTests extends AbstractClientIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(TelegramTextClient.class);

    @Autowired
    private TelegramTextClient telegramTextClient;

    @Test
    void testSendText() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramTextClient.sendText(sendTextRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("zgrn [telegram-starter] Сообщение с текстом text успешно отправлено");
            });
    }

    @Test
    void testSendEditText() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramTextClient.sendEditText(sendEditTextRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("rvak [telegram-starter] Видео с текстом text успешно отредактировано");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static SendTextRequest sendTextRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);
        Message message = mock(Message.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);
        when(sendResponse.message())
            .thenReturn(message);
        when(message.messageId())
            .thenReturn(1);

        return new SendTextRequest("text", telegramBot);
    }

    private static SendEditTextRequest sendEditTextRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        return new SendEditTextRequest("text", 1, telegramBot);
    }
}

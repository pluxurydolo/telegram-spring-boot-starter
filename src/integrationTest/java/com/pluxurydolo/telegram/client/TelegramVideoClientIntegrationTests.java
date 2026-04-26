package com.pluxurydolo.telegram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.response.SendResponse;
import com.pluxurydolo.telegram.base.AbstractClientIntegrationTests;
import com.pluxurydolo.telegram.dto.request.SendVideoRequest;
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

class TelegramVideoClientIntegrationTests extends AbstractClientIntegrationTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(TelegramVideoClient.class);

    @Autowired
    private TelegramVideoClient telegramVideoClient;

    @Test
    void testSendVideo() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramVideoClient.sendVideo(sendVideoRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("nevj [telegram-starter] Видео с подписью caption успешно отправлено");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static SendVideoRequest sendVideoRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        SendResponse sendResponse = mock(SendResponse.class);

        when(telegramBot.execute(any()))
            .thenReturn(sendResponse);

        return new SendVideoRequest(bytes(), "caption", telegramBot);
    }

    private static byte[] bytes() {
        return new byte[]{};
    }
}

package com.pluxurydolo.telegram.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pluxurydolo.telegram.base.AbstractTelegramClientTests;
import com.pluxurydolo.telegram.dto.request.GetFileRequest;
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

class TelegramFileClientIntegrationTests extends AbstractTelegramClientTests {
    private static final AppenderAttachable<ILoggingEvent> LOGGER =
        (Logger) getLogger(TelegramFileClient.class);

    @Autowired
    private TelegramFileClient telegramFileClient;

    @Test
    void testGetFile() {
        List<ILoggingEvent> logs = listAppender().list;

        telegramFileClient.getFile(getFileRequest())
            .subscribe();

        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
                assertThat(logs)
                    .hasSize(1);

                assertThat(logs.getFirst().getFormattedMessage())
                    .isEqualTo("gqgy [telegram-starter] Файл fileId успешно получен");
            });
    }

    private static ListAppender<ILoggingEvent> listAppender() {
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        LOGGER.addAppender(listAppender);
        return listAppender;
    }

    private static GetFileRequest getFileRequest() {
        TelegramBot telegramBot = mock(TelegramBot.class);
        GetFileResponse getFileResponse = mock(GetFileResponse.class);
        File file = mock(File.class);

        when(telegramBot.execute(any()))
            .thenReturn(getFileResponse);
        when(getFileResponse.file())
            .thenReturn(file);
        when(file.filePath())
            .thenReturn("filePath");

        return new GetFileRequest("fileId", telegramBot);
    }
}

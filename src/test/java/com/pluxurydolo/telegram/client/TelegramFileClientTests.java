package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pluxurydolo.telegram.dto.request.GetFileRequest;
import com.pluxurydolo.telegram.exception.GetFileException;
import com.pluxurydolo.telegram.properties.TelegramApiProperties;
import com.pluxurydolo.telegram.util.MediaRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramFileClientTests {

    @Mock
    private TelegramApiProperties telegramApiProperties;

    @Mock
    private MediaRetriever mediaRetriever;

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private GetFileResponse getFileResponse;

    @Mock
    private File file;

    @InjectMocks
    private TelegramFileClient telegramFileClient;

    @BeforeEach
    void setUp() {
        when(telegramApiProperties.fileUri())
            .thenReturn("fileUri");
    }

    @Test
    void testGetFile() {
        byte[] bytes = {};
        when(telegramBot.execute(any()))
            .thenReturn(getFileResponse);
        when(getFileResponse.file())
            .thenReturn(file);
        when(file.filePath())
            .thenReturn("filePath");
        when(mediaRetriever.retrieve(anyString()))
            .thenReturn(Mono.just(bytes));

        Mono<byte[]> result = telegramFileClient.getFile(getFileRequest(telegramBot));

        create(result)
            .expectNext(bytes)
            .verifyComplete();
    }

    @Test
    void testGetFileWhenExceptionOccurred() {
        doThrow(RuntimeException.class)
            .when(telegramBot).execute(any());

        Mono<byte[]> result = telegramFileClient.getFile(getFileRequest(telegramBot));

        create(result)
            .verifyErrorMatches(throwable -> throwable.getClass().equals(GetFileException.class));
    }

    private static GetFileRequest getFileRequest(TelegramBot telegramBot) {
        return new GetFileRequest("fileUri", telegramBot);
    }
}

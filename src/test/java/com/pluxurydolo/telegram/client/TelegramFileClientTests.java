package com.pluxurydolo.telegram.client;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pluxurydolo.telegram.dto.request.GetFileRequest;
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
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static reactor.test.StepVerifier.create;

@ExtendWith(MockitoExtension.class)
class TelegramFileClientTests {

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
        setField(telegramFileClient, "fileUri", "fileUri");
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
            .expectError(RuntimeException.class)
            .verify();
    }

    private static GetFileRequest getFileRequest(TelegramBot telegramBot) {
        return new GetFileRequest("fileUri", telegramBot);
    }


}

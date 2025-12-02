package com.pluxurydolo.telegram.parser;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramUpdateParserTests {

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private User user;

    private static final TelegramUpdateParser PARSER = new TelegramUpdateParser();

    @Test
    void testGetSenderId() {
        when(update.message())
            .thenReturn(message);
        when(message.from())
            .thenReturn(user);
        when(user.id())
            .thenReturn(1L);

        long senderId = PARSER.getSenderId(update);

        assertThat(senderId).isOne();
    }
}

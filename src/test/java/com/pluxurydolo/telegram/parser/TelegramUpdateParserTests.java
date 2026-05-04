package com.pluxurydolo.telegram.parser;

import com.pengrad.telegrambot.model.CallbackQuery;
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
    private static final TelegramUpdateParser PARSER = new TelegramUpdateParser();

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private CallbackQuery callbackQuery;

    @Mock
    private User user;

    @Test
    void testGetSenderIdWhenUpdateIsMessage() {
        when(update.message())
            .thenReturn(message);
        when(message.from())
            .thenReturn(user);
        when(user.id())
            .thenReturn(1L);

        long result = PARSER.getSenderId(update);

        assertThat(result)
            .isOne();
    }

    @Test
    void testGetSenderIdWhenUpdateIsCallback() {
        when(update.callbackQuery())
            .thenReturn(callbackQuery);
        when(callbackQuery.from())
            .thenReturn(user);
        when(user.id())
            .thenReturn(1L);

        long result = PARSER.getSenderId(update);

        assertThat(result)
            .isOne();
    }

    @Test
    void testGetSenderIdWhenUpdateIsEmpty() {
        long result = PARSER.getSenderId(update);

        assertThat(result)
            .isZero();
    }

    @Test
    void testGetTextWhenUpdateIsMessage() {
        when(update.message())
            .thenReturn(message);
        when(message.text())
            .thenReturn("text");

        String result = PARSER.getText(update);

        assertThat(result)
            .isEqualTo("text");
    }

    @Test
    void testGetTextWhenUpdateIsCallback() {
        when(update.callbackQuery())
            .thenReturn(callbackQuery);
        when(callbackQuery.data())
            .thenReturn("data");

        String result = PARSER.getText(update);

        assertThat(result)
            .isEqualTo("data");
    }

    @Test
    void testGetTextWhenUpdateIsEmpty() {
        String result = PARSER.getText(update);

        assertThat(result)
            .isBlank();
    }
}

package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pluxurydolo.telegram.application.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static reactor.test.StepVerifier.create;

@SpringBootTest(classes = TestApplication.class)
@TestPropertySource(properties = "telegram.allowed-user-id=123")
class FilterExecutorTests {

    @Autowired
    private FilterExecutor filterExecutor;

    @Test
    void testExecute() {
        Mono<Boolean> result = filterExecutor.execute(textUpdate(123L));

        create(result)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    void testExecuteWithProhibitedSenderId() {
        Mono<Boolean> result = filterExecutor.execute(textUpdate(456L));

        create(result)
            .expectNext(false)
            .verifyComplete();
    }

    private static Update textUpdate(long senderId) {
        Update update = new Update();
        setField(update, "message", textMessage(senderId, "text"));
        return update;
    }

    private static Message textMessage(long senderId, String text) {
        Message message = new Message();
        setField(message, "from", user(senderId));
        setField(message, "text", text);
        return message;
    }

    private static User user(long senderId) {
        User user = mock(User.class);

        when(user.id())
            .thenReturn(senderId);

        return user;
    }
}

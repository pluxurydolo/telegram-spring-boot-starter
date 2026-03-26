package com.pluxurydolo.telegram.util;

import com.pluxurydolo.telegram.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static reactor.test.StepVerifier.create;

@SpringBootTest(classes = TestApplication.class)
class MediaRetrieverTests {

    @Autowired
    private MediaRetriever mediaRetriever;

    @Test
    void testRetrieve() {
        String path = "https://assets-prd.ignimgs.com/2021/12/17/gta-5-button-2021-1639777058682.jpg?crop=1%3A1%2Csmart&format=jpg&auto=webp&quality=80";
        Mono<byte[]> result = mediaRetriever.retrieve(path);

        create(result)
            .expectNextMatches(bytes -> {
                assertThat(bytes)
                    .hasSize(250421);
                return true;
            })
            .verifyComplete();
    }

    @Test
    void testRetrieveWhenExceptionOccurred() {
        String path = "https://incorrect-path.com/images/1";
        Mono<byte[]> result = mediaRetriever.retrieve(path);

        create(result)
            .expectError(UnknownHostException.class)
            .verify();
    }
}

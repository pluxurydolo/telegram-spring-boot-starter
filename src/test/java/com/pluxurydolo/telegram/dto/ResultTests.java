package com.pluxurydolo.telegram.dto;

import org.junit.jupiter.api.Test;

import static com.pluxurydolo.telegram.dto.Result.FAILURE;
import static com.pluxurydolo.telegram.dto.Result.SUCCESS;
import static com.pluxurydolo.telegram.dto.Result.fromBoolean;
import static org.assertj.core.api.Assertions.assertThat;

class ResultTests {

    @Test
    void testFromBoolean() {
        Result success = fromBoolean(true);
        Result failure = fromBoolean(false);

        assertThat(success)
            .isEqualTo(SUCCESS);
        assertThat(failure)
            .isEqualTo(FAILURE);
    }
}

package com.pluxurydolo.telegram.ratelimiter.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimitExceededMessageBuilderTests {
    private static final RateLimitExceededMessageBuilder BUILDER = new RateLimitExceededMessageBuilder();

    @Test
    void testBuildWhenCooldownIsAboveZero() {
        String result = BUILDER.build(1L);

        assertThat(result)
            .isEqualTo(
                """
                    ❌ Превышено количество запросов.
                    Ожидайте 1 секунд"""
            );
    }

    @Test
    void testBuildWhenCooldownIsEqualToZero() {
        String result = BUILDER.build(0L);

        assertThat(result)
            .isEqualTo("✅ Можно продолжить отправку запросов");
    }

    @Test
    void testBuildWhenCooldownIsBelowZero() {
        String result = BUILDER.build(-1L);

        assertThat(result)
            .isEqualTo("✅ Можно продолжить отправку запросов");
    }

}

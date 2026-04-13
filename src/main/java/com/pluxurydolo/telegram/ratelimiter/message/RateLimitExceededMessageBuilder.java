package com.pluxurydolo.telegram.ratelimiter.message;

public class RateLimitExceededMessageBuilder {
    public String build(long cooldown) {
        if (cooldown > 0) {
            return fail(cooldown);
        }
        return success();
    }

    private static String success() {
        return "✅ Можно продолжить отправку запросов";
    }

    private static String fail(long cooldown) {
        return String.format("""
                ❌ Превышено количество запросов.
                Ожидайте %s секунд""",
            cooldown
        );
    }
}

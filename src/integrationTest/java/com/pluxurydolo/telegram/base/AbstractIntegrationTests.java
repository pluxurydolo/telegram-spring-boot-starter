package com.pluxurydolo.telegram.base;

import com.pluxurydolo.telegram.TestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = TestApplication.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "telegram.enabled=true",
    "telegram.rate-limit.threshold=1",
    "telegram.rate-limit.refresh.period=1m"
})
public abstract class AbstractIntegrationTests {
}

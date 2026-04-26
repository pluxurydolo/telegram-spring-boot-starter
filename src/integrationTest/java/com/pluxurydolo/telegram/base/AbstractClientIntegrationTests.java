package com.pluxurydolo.telegram.base;

import com.pluxurydolo.telegram.configuration.MediaRetrieverTestConfiguration;
import org.springframework.context.annotation.Import;

@Import(MediaRetrieverTestConfiguration.class)
public abstract class AbstractClientIntegrationTests extends AbstractIntegrationTests {
}

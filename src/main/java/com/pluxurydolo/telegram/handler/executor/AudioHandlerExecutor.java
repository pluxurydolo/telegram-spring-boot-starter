package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.media.AbstractAudioHandler;

import java.util.List;

public class AudioHandlerExecutor extends AbstractHandlerExecutor {
    public AudioHandlerExecutor(List<AbstractAudioHandler> handlers) {
        super(handlers);
    }
}

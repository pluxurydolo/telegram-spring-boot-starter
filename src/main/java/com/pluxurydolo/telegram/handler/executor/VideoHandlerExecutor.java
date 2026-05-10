package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.media.AbstractVideoHandler;

import java.util.List;

public class VideoHandlerExecutor extends AbstractHandlerExecutor {
    public VideoHandlerExecutor(List<AbstractVideoHandler> handlers) {
        super(handlers);
    }
}

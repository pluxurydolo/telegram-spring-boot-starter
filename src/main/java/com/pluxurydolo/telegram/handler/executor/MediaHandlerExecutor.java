package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.media.AbstractMediaHandler;

import java.util.List;

public class MediaHandlerExecutor extends AbstractHandlerExecutor {
    public MediaHandlerExecutor(List<AbstractMediaHandler> handlers) {
        super(handlers);
    }
}

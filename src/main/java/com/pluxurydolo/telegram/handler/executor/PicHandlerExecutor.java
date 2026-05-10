package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.media.AbstractPicHandler;

import java.util.List;

public class PicHandlerExecutor extends AbstractHandlerExecutor {
    public PicHandlerExecutor(List<AbstractPicHandler> handlers) {
        super(handlers);
    }
}

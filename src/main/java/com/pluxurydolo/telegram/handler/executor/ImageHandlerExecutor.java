package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.media.AbstractImageHandler;

import java.util.List;

public class ImageHandlerExecutor extends AbstractHandlerExecutor {
    public ImageHandlerExecutor(List<AbstractImageHandler> handlers) {
        super(handlers);
    }
}

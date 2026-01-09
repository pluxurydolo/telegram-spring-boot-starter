package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.text.AbstractTextHandler;

import java.util.List;

public class TextHandlerExecutor extends AbstractHandlerExecutor {
    public TextHandlerExecutor(List<AbstractTextHandler> handlers) {
        super(handlers);
    }
}

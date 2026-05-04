package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.callback.AbstractCallbackHandler;

import java.util.List;

public class CallbackHandlerExecutor extends AbstractHandlerExecutor {
    public CallbackHandlerExecutor(List<AbstractCallbackHandler> handlers) {
        super(handlers);
    }
}

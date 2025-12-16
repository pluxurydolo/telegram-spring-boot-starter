package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.text.AbstractCommandHandler;

import java.util.List;

public class CommandHandlerExecutor extends AbstractHandlerExecutor {
    public CommandHandlerExecutor(List<AbstractCommandHandler> handlers) {
        super(handlers);
    }
}

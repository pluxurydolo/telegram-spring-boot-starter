package com.pluxurydolo.telegram.handler.executor;

import com.pluxurydolo.telegram.handler.text.AbstractLinkHandler;

import java.util.List;

public class LinkHandlerExecutor extends AbstractHandlerExecutor {
    public LinkHandlerExecutor(List<AbstractLinkHandler> handlers) {
        super(handlers);
    }
}

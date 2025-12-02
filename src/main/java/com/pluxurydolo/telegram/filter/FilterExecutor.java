package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FilterExecutor {
    private final List<Filter> filters;

    public FilterExecutor(List<Filter> filters) {
        this.filters = filters;
    }

    public Mono<Boolean> execute(Update update) {
        return Flux.fromIterable(filters)
            .map(filter -> filter.doFilter(update))
            .all(result -> result);
    }
}

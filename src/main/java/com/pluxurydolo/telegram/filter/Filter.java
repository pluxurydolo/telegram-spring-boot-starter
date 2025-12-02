package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Update;

public interface Filter {
    boolean doFilter(Update update);
}

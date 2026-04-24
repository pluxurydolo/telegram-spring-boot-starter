package com.pluxurydolo.telegram.filter;

import com.pengrad.telegrambot.model.Update;
import com.pluxurydolo.telegram.dto.Result;

public interface Filter {
    Result doFilter(Update update);
}

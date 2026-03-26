package com.pluxurydolo.telegram.util;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;

import static java.net.URI.create;

public class MediaRetriever {
    public Mono<byte[]> retrieve(String path) {
        return Mono.fromCallable(
                () -> {
                    try (InputStream inputStream = create(path).toURL().openStream()) {
                        return inputStream.readAllBytes();
                    }
                }
            )
            .subscribeOn(Schedulers.boundedElastic());
    }
}

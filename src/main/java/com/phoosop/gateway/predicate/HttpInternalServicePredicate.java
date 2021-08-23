package com.phoosop.gateway.predicate;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

public class HttpInternalServicePredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {

        if (throwable instanceof ResponseStatusException exception) {
            return exception.getStatus().is5xxServerError();
        }
        return throwable instanceof TimeoutException || throwable instanceof HttpServerErrorException;

    }

}

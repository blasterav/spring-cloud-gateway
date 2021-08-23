package com.phoosop.gateway.exception;

import com.phoosop.gateway.model.Response;
import com.phoosop.gateway.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static com.phoosop.gateway.exception.StatusConstants.HttpConstants;

@Component
@Order(-2)
public class CustomErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(CustomErrorWebExceptionHandler.class);

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {

    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable throwable = getError(request);
        if (throwable instanceof ServiceException exception) {
            LOG.error("Failed {} {}: {}, {}", request.methodName(), request.uri().getPath(), exception.getStatus().getCode(), exception.getStatus().getDesc());
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(new Response<>(new Status(exception.getStatus()), null)));

        } else {
            return this.internalServerError(request, throwable);
        }

    }

    private Mono<ServerResponse> internalServerError(ServerRequest request, Throwable error) {
        LOG.error("Failed {} {}: {}", request.methodName(), request.uri().getPath(), error.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Response<>(new Status(HttpConstants.INTERNAL_SERVER_ERROR), null)));
    }
}


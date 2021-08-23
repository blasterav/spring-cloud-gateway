package com.phoosop.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;


@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    private final Logger logger = LoggerFactory.getLogger(LoggingGatewayFilterFactory.class);

    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (config.isPreLogger() || config.isPostLogger()) {
                Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
                String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
                Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
                URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);

                if (config.isPreLogger()) {
                    // Pre-processing
                    logger.info("Pre: {} is routed to id: {}, uri: {}", originalUri, route.getId(), routeUri);
                }
                return chain.filter(exchange)
                        .then(Mono.fromRunnable(() -> {
                            // Post-processing
                            if (config.isPostLogger()) {
                                HttpStatus httpStatus = exchange.getResponse().getStatusCode();
                                logger.info("Post: {} is routed to id: {}, uri: {}, response status: {}", originalUri, route.getId(), routeUri, httpStatus.value());
                            }
                        }));
            }
            return chain.filter(exchange);
        };
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {

        private boolean preLogger;
        private boolean postLogger;

    }
}

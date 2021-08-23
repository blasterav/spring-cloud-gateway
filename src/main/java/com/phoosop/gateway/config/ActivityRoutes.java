package com.phoosop.gateway.config;

import com.phoosop.gateway.filter.LoggingGatewayFilterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ActivityRoutes {

    private final LoggingGatewayFilterFactory loggingGatewayFilterFactory;

    public Buildable<Route> normal(PredicateSpec spec) {
        return spec.path("/api/activity")
                .uri("http://localhost:9100");
    }

    public Buildable<Route> stripprefix(PredicateSpec spec) {
        return spec.path("/stripprefix/addprefix/activity")
                .filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.stripPrefix(2);
                    gatewayFilterSpec.prefixPath("/api");
                    return gatewayFilterSpec;
                })
                .uri("http://localhost:9100");
    }

    public Buildable<Route> rewrite(PredicateSpec spec) {
        return spec.path("/rewrite")
                .filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.rewritePath("/rewrite", "/api/activity");
                    return gatewayFilterSpec;
                })
                .uri("http://localhost:9100");
    }

    public Buildable<Route> logging(PredicateSpec spec) {
        return spec.path("/logging")
                .filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.rewritePath("/logging", "/api/activity")
                            .filter(loggingGatewayFilterFactory.apply(new LoggingGatewayFilterFactory.Config(true, true)));
                    return gatewayFilterSpec;
                })
                .uri("http://localhost:9100");
    }

    public Buildable<Route> circuit(PredicateSpec spec) {
        return spec.path("/circuit")
                .filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.rewritePath("/circuit", "/api/activity")
                            .circuitBreaker(config -> config.setName("circuit")
                                    .setFallbackUri("forward:/fallback/unavailable-fallback"));
                    return gatewayFilterSpec;
                })
                .uri("http://localhost:9100");
    }

}

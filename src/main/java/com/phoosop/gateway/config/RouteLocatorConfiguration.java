package com.phoosop.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteLocatorConfiguration {

    private final ActivityRoutes activityRoutes;
    private final SignatureRoutes signatureRoutes;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(activityRoutes::normal)
                .route(activityRoutes::stripprefix)
                .route(activityRoutes::rewrite)
                .route(activityRoutes::logging)
                .route(activityRoutes::circuit)
                .route(signatureRoutes::signature)
                .build();
    }

}

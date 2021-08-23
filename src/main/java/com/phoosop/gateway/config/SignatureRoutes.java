package com.phoosop.gateway.config;

import com.phoosop.gateway.filter.VerifyResponseGatewayFilterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SignatureRoutes {

    private final VerifyResponseGatewayFilterFactory verifyResponseGatewayFilterFactory;

    public Buildable<Route> signature(PredicateSpec spec) {
        return spec.path("/signature")
                .filters(gatewayFilterSpec -> {
                    gatewayFilterSpec.rewritePath("/signature", "/api/signature")
                            .filter(verifyResponseGatewayFilterFactory.apply(new VerifyResponseGatewayFilterFactory.Config()));
                    return gatewayFilterSpec;
                })
                .uri("http://localhost:9100");
    }

}

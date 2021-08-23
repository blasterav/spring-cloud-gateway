package com.phoosop.gateway.filter;

import com.phoosop.gateway.exception.ServiceException;
import com.phoosop.gateway.exception.StatusConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;


@Component
public class VerifyResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<VerifyResponseGatewayFilterFactory.Config> {

    public VerifyResponseGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> Mono.fromCallable(() -> UUID.randomUUID().toString())
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(signatureRequest -> exchange.getRequest().mutate()
                        .headers(httpHeaders -> httpHeaders.add("Signature", signatureRequest)))
                .flatMap(builder -> chain.filter(exchange))
                .then(Mono.fromRunnable(() -> {
                    String signatureResponse = exchange.getResponse().getHeaders().get("Signature").get(0);
                    if (!signatureResponse.equals("alex")) {
                        throw new ServiceException(StatusConstants.HttpConstants.INVALID_SIGNATURE);
                    }
                }));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Config {

    }
}

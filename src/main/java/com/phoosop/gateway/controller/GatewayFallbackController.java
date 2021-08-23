package com.phoosop.gateway.controller;

import com.phoosop.gateway.exception.StatusConstants;
import com.phoosop.gateway.model.Response;
import com.phoosop.gateway.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class GatewayFallbackController {

    @GetMapping("/unavailable-fallback")
    public Mono<ResponseEntity<Response<Void>>> getFallBackBackendA() {
        Response<Void> response = new Response<Void>(new Status(StatusConstants.HttpConstants.SERVICE_UNAVAILABLE), null);
        ResponseEntity<Response<Void>> responseEntity = new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        return Mono.just(responseEntity);
    }

}

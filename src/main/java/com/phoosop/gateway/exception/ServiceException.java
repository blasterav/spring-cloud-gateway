package com.phoosop.gateway.exception;

import lombok.Getter;

import static com.phoosop.gateway.exception.StatusConstants.HttpConstants;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpConstants status;
    private String text;

    public ServiceException(HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
    }

    public ServiceException(String text, HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
        this.text = text;
    }

}

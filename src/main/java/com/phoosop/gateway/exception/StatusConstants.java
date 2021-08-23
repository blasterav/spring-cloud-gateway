package com.phoosop.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StatusConstants {

    @Getter
    @AllArgsConstructor
    public enum HttpConstants {

        SUCCESS("0", "Success"),
        INVALID_SIGNATURE("TEMP00001", "Invalid signature"),

        SERVICE_UNAVAILABLE("TEMP31993", "Service Unavailable"),
        METHOD_NOT_ALLOWED("TEMP31994", "Method not allowed"),
        JSON_DECODING_ERROR("TEMP31995", "JSON Decoding error"),
        NO_MATCHING_HANDLER("TEMP31996", "No matching handler"),
        NOT_FOUND("TEMP31997", "Not found"),
        BAD_REQUEST("TEMP31998", "Bad request"),
        INTERNAL_SERVER_ERROR("TEMP31999", "Internal server error");

        private final String code;
        private final String desc;

    }

}

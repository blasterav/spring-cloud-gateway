package com.phoosop.gateway.model;

import com.phoosop.gateway.exception.StatusConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Status {

    private final String code;

    private final String message;

    public Status(StatusConstants.HttpConstants httpConstantsExpect) {
        this.code = httpConstantsExpect.getCode();
        this.message = httpConstantsExpect.getDesc();
    }

}

package org.chris.service.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {

    private static final long serialVersionUID = 3508978946006812925L;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}

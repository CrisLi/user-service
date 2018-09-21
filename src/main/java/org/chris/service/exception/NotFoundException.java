package org.chris.service.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {

    private static final long serialVersionUID = 5275539061833656811L;

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}

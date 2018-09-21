package org.chris.service.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RestException {

    private static final long serialVersionUID = -5253519433067403046L;

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}

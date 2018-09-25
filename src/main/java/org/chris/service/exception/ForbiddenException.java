package org.chris.service.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RestException {

    private static final long serialVersionUID = -7742552067547534584L;

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

}

package org.chris.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RestException extends ResponseStatusException {

    private static final long serialVersionUID = 6569601279785926831L;

    public RestException(HttpStatus status, String message) {
        super(status, message);
    }

}

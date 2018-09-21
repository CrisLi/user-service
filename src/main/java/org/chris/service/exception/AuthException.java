package org.chris.service.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RestException {

    private static final long serialVersionUID = -3448896457225575021L;

    public AuthException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

}

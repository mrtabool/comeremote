package com.comeremote.web.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;


public class CRUDMethodNotSupportedException extends AuthenticationServiceException {

    public CRUDMethodNotSupportedException(String msg) {
        super(msg);
    }

}

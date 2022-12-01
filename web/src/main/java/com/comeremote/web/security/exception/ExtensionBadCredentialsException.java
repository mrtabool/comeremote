package com.comeremote.web.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class ExtensionBadCredentialsException extends BadCredentialsException {

    public ExtensionBadCredentialsException(Class<?> type, Long id) {
        super("could not find " + type.getSimpleName() + " with id " + id);
    }

    public ExtensionBadCredentialsException(String login) {
        super("user with login " + login + " does not exists");
    }
}

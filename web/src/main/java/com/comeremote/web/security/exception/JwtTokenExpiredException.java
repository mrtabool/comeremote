package com.comeremote.web.security.exception;

import com.comeremote.web.security.token.JwtToken;
import org.springframework.security.core.AuthenticationException;


public class JwtTokenExpiredException extends AuthenticationException {

    transient private JwtToken token;

    public JwtTokenExpiredException(String msg) {
        super(msg);
    }

    public JwtTokenExpiredException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

}

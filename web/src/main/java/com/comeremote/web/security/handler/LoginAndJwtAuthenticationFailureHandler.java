package com.comeremote.web.security.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.comeremote.web.security.exception.CRUDMethodNotSupportedException;
import com.comeremote.web.security.exception.ErrorResponse;
import com.comeremote.web.security.exception.JwtTokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginAndJwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {

        log.debug("Failed to authenticate: " + e.getMessage(), e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        if (e instanceof JwtTokenExpiredException) {
            mapper.writeValue(response.getWriter(), new ErrorResponse("Token has expired", HttpStatus.UNAUTHORIZED));
        } else if (e instanceof CRUDMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(), new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
        } else if (e instanceof UsernameNotFoundException) {
            mapper.writeValue(response.getWriter(), new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
        }

        mapper.writeValue(response.getWriter(), new ErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
    }

}

package com.comeremote.web.security.handler;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.comeremote.web.security.model.UserDetailsImpl;
import com.comeremote.web.security.response.LoginJwtResponse;
import com.comeremote.web.security.service.JwtUtilService;
import com.comeremote.web.security.token.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtilService jwtUtilService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken sourceToken = (UsernamePasswordAuthenticationToken) authentication;
        UserDetailsImpl authenticationDetails = (UserDetailsImpl) sourceToken.getDetails();

        JwtToken jwtToken = jwtUtilService.generateToken(authenticationDetails);

        log.debug("Generating token for authenticated user");
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");

        Set<String> roles = authenticationDetails.getRoles();
//                authenticationDetails
//                .getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .findFirst()
//                .orElseThrow(RoleNullPointerException::new);

        objectMapper.writeValue(response.getWriter(),
                new LoginJwtResponse(authenticationDetails.getUsername(),
                                     authenticationDetails.getEmail(),
                                     roles,
                                     jwtToken,
                                     HttpStatus.OK.value(),
                                     "Ok"));

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}

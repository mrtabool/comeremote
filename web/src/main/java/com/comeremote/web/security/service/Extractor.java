package com.comeremote.web.security.service;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import com.comeremote.db.entity.User;
import com.comeremote.db.repository.UserRepository;
import com.comeremote.web.exception.UserNotFoundException;
import com.comeremote.web.security.RequestHeaders;
import com.comeremote.web.security.token.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Extractor {

    private final UserRepository userRepository;
    private final JwtUtilService jwtUtilService;

    public static final String HEADER_PREFIX = "Bearer ";

    public String extractJwt(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }
        log.debug("Attempting to extract token");
        return header.substring(HEADER_PREFIX.length());
    }

    public String extractLogin(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(RequestHeaders.HEADER_PARAM_JWT_TOKEN);
        String jwt = extractJwt(authorizationHeader);

        return jwtUtilService.extractLogin(new JwtToken(jwt));
    }

    public User extractUser(HttpServletRequest request) {
        String userLogin = extractLogin(request);

        Optional<User> user;

        if (userLogin.contains("@")) {
            user = userRepository.findByEmail(userLogin);
        } else {
            user = userRepository.findByUsername(userLogin);
        }
        return user.orElseThrow(() -> new UserNotFoundException(userLogin));
    }
}

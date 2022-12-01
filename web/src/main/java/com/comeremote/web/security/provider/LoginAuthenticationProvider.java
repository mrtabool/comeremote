package com.comeremote.web.security.provider;

import java.util.Optional;
import com.comeremote.db.entity.User;
import com.comeremote.db.repository.UserRepository;
import com.comeremote.web.security.exception.ExtensionBadCredentialsException;
import com.comeremote.web.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Attempting to verify user credentials");

        Assert.notNull(authentication, "No authentication data provided");

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        String requestUserLogin = (String) authenticationToken.getPrincipal();
        String requestPassword = (String) authenticationToken.getCredentials();

        Optional<User> userOptional;

        if (requestUserLogin.contains("@")) {
            userOptional = userRepository.findByEmailAndActive(requestUserLogin, true);
        } else {
            userOptional = userRepository.findByUsernameAndActive(requestUserLogin, true);
        }

        User user = userOptional.orElseThrow(() -> new ExtensionBadCredentialsException(requestUserLogin));

        if (!encoder.matches(requestPassword, user.getPassword())) {
            throw new AuthenticationServiceException("wrong password");
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getId(), null, null);
        token.setDetails(userDetails);

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

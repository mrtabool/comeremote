package com.comeremote.web.security.response;

import java.util.Set;
import com.comeremote.web.security.token.JwtToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginJwtResponse {

    private final String username;
    private final String email;
    private final Set<String> roles;
    private final JwtToken token;
    private final int status;
    private final String message;

}

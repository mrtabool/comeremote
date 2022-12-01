package com.comeremote.web.security.provider;


import com.comeremote.web.security.service.JwtUtilService;
import com.comeremote.web.security.token.JwtAuthenticationToken;
import com.comeremote.web.security.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtUtilService jwtUtilService;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationProvider(JwtUtilService jwtUtilService, UserDetailsService userDetailsService) {
		this.jwtUtilService = jwtUtilService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Attempting to parse token");
		String userLogin = jwtUtilService.extractLogin(new JwtToken((String) authentication.getCredentials()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);

		return new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(JwtAuthenticationToken.class);
	}

}

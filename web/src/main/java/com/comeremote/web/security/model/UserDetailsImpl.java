package com.comeremote.web.security.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.comeremote.db.entity.Role;
import com.comeremote.db.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final Long userId;
    private final String username;
    private final String email;
    private final String password;
    private final boolean active;
    private final UUID activationCode;
    private final List<GrantedAuthority> authorities;
    private final Set<String> roles;
    private final boolean tokenActive = true;

    public UserDetailsImpl(User user) {
        this.active = user.isActive();
        this.activationCode = user.getActivationCode();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userId = user.getId();
        this.roles =  user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public Long getUserId() {
        return userId;
    }

}

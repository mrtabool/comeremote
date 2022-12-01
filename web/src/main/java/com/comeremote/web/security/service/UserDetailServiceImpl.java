package com.comeremote.web.security.service;

import java.util.Optional;
import com.comeremote.db.entity.User;
import com.comeremote.db.repository.UserRepository;
import com.comeremote.web.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user;

        if (login.contains("@")) {
            user = userRepository.findByEmail(login);
        } else {
            user = userRepository.findByUsername(login);
        }

        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("Авторизація не вдалася. Користувача з емейлом " + login + " не існує"));
    }
}

package com.comeremote.web.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.comeremote.db.entity.Role;
import com.comeremote.db.entity.User;
import com.comeremote.db.repository.RoleRepository;
import com.comeremote.db.repository.UserRepository;
import com.comeremote.web.dto.UserDto;
import com.comeremote.web.exception.IllegalApiArgumentException;
import com.comeremote.web.exception.UserAlreadyExistException;
import com.comeremote.web.exception.UserNotFoundException;
import com.comeremote.web.security.exception.NoSuchRoleException;
import com.comeremote.web.security.model.RegistrationRequest;
import com.comeremote.web.security.model.UserDetailsImpl;
import com.comeremote.web.security.response.LoginJwtResponse;
import com.comeremote.web.security.service.Extractor;
import com.comeremote.web.security.service.JwtUtilService;
import com.comeremote.web.security.token.JwtToken;
import com.comeremote.web.service.converter.UserConverter;
import com.comeremote.web.util.RoleName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${application.local.domain}")
    private String domain;
    @Value("${user.activation.url}")
    private String activationUrl;

    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final Extractor extractor;
    private final JwtUtilService jwtUtilService;
    private final UserConverter converter;
    private final SimpleMailSender simpleMailSender;

    public UserDto own(HttpServletRequest request) {
        User user = extractor.extractUser(request);
        return converter.convertUserToDto(user);
    }

    public LoginJwtResponse saveUser(RegistrationRequest registrationRequest) {
        log.debug("Attempting to register new client.");

        String username = registrationRequest.getUsername();
        String email = registrationRequest.getEmail();

        User userFromDB = null;

        userFromDB = userRepository.findByUsername(username).orElse(null);
        if (userFromDB != null) {
            throw new UserAlreadyExistException("username " + registrationRequest.getUsername() + " is taken");
        }

        userFromDB = userRepository.findByEmail(email).orElse(null);
        if (userFromDB != null) {
            throw new UserAlreadyExistException("this email is already registered");
        }

        Role role = roleRepository.findByName(RoleName.USER).orElseThrow(() -> new NoSuchRoleException(RoleName.USER));

        User user = new User();
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        setUserPassword(user, registrationRequest.getPassword());
        user.getRoles().add(role);

        simpleMailSender.send(user.getEmail(),
                              "Account activation",
                              String.format(
                                "Hello %s! \n" +
                                        "Welcome to ComeRemote. " +
                                        "Please visit next link %s%s%s",
                                user.getUsername(),
                                domain,
                                activationUrl,
                                user.getActivationCode()
                        ));

        User savedUser = userRepository.save(user);
        UserDetailsImpl userDetails = new UserDetailsImpl(savedUser);
        JwtToken jwtToken = jwtUtilService.generateToken(userDetails);

        return new LoginJwtResponse(
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                jwtToken,
                HttpStatus.OK.value(),
                "OK");
    }

    public void activateUser(UUID code) {
        userRepository.findByActivationCode(code)
                .ifPresentOrElse(
                        user -> {
                            user.setActivationCode(null);
                            user.setActive(true);
                            userRepository.save(user);
                        },
                        () -> {
                            throw new IllegalApiArgumentException("wrong activation code");
                        });
    }

    public List<UserDetails> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserDetailsImpl::new).collect(Collectors.toList());
    }

    public UserDetails findById(Integer id) {

        Optional<User> user = userRepository.findById(id);

        return user.map(UserDetailsImpl::new).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void updatePassword(HttpServletRequest request,
                               String oldPassword,
                               String newPassword,
                               String repeatNewPassword) {

        User user = extractor.extractUser(request);

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        if (!newPassword.equals(repeatNewPassword)) {
            throw new BadCredentialsException("passwords don't match");
        }
        setUserPassword(user, newPassword);
        userRepository.save(user);
    }

    public void updateEmail(HttpServletRequest request, String email) {
        User user = extractor.extractUser(request);
        user.setEmail(email);

        userRepository.save(user);
    }

    public void deleteUser(Integer id) {

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

    public void addRole(Integer id, String roleName) {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new NoSuchRoleException(roleName));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getRoles().contains(role))
            throw new IllegalApiArgumentException("user already has this role");

        user.getRoles().add(role);
        userRepository.save(user);
    }

    private void setUserPassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
    }

}

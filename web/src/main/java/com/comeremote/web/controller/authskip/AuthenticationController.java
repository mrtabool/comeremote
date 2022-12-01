package com.comeremote.web.controller.authskip;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import com.comeremote.web.security.model.AuthenticationRequest;
import com.comeremote.web.security.model.RegistrationRequest;
import com.comeremote.web.security.response.LoginJwtResponse;
import com.comeremote.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Registration and Authentication.")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(value = "/authenticate", produces = {"application/json"})
    @ApiOperation(
            value = "Authenticates user and creates a token.",
            notes = "Provide login and password for authentication.",
            response = ResponseEntity.class
    )
    public ResponseEntity<LoginJwtResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return null;
    }

    @PostMapping("/register")
    @ApiOperation(
            value = "Registers a new user.",
            notes = "Provide Registration form fo registration.",
            response = ResponseEntity.class
    )
    public ResponseEntity<LoginJwtResponse> register(
            @Valid @RequestBody RegistrationRequest registrationRequest) {
        LoginJwtResponse emailJwtResponse = userService.saveUser(registrationRequest);
        return new ResponseEntity<>(emailJwtResponse, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Activates user",
            notes = "Provide activation code",
            response = String.class
    )
    @GetMapping("/activate/{code}")
    public String activate(
            @ApiParam(value = "activation code",
                    required = true)
            @NotBlank(message = "Should be not blank") @PathVariable UUID code) {
        userService.activateUser(code);
        return "Welcome to ComeRemote";
    }
}

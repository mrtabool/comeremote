package com.comeremote.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.comeremote.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
@Api(value = "UserController")
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(
            value = "Shows all users.",
            response = List.class,
            authorizations = @Authorization("Authorization"))
    public List<UserDetails> findAll() {
        return userService.findAll();
    }

}

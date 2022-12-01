package com.comeremote.web.controller.authskip;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Registration and Authentication.")
public class HomeController {

    @ApiOperation(
            value = "Home Page",
            response = String.class
    )
    @GetMapping(value = "/")
    public String index() {
        return "Welcome to ComeRemote";
    }
}

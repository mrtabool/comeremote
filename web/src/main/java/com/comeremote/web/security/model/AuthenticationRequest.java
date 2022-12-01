package com.comeremote.web.security.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @ApiModelProperty(position = 2)
    private String login;

    @ApiModelProperty(position = 3)
    private String password;

}

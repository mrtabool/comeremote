package com.comeremote.web.security.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @Size(min = 3, max = 60)
    @NotBlank(message = "Should be not blank")
    private String username;

    @Size(max = 100)
    @Email(regexp = "^([a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6})?()$",
            message = "Should be email")
    private String email;

    @Size(min = 4, max = 60)
    @NotBlank(message = "Should be not blank")
    private String password;

}

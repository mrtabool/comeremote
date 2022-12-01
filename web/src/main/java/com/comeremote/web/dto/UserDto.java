package com.comeremote.web.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.comeremote.db.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(e -> e.getName()).collect(Collectors.toSet());
    }

    private String username;

    private String email;

    private Set<String> roles;

    private Integer version;
}

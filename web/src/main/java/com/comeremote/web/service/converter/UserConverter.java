package com.comeremote.web.service.converter;

import com.comeremote.db.entity.User;
import com.comeremote.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertUserToDto(User user) {
        return new UserDto(user);
    }

}

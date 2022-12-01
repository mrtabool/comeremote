package com.comeremote.web.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id) {
        super("Could not find user with id " + id);
    }


    public UserNotFoundException(String login) {
        super("user with login " + login + " is not exists");
    }

}

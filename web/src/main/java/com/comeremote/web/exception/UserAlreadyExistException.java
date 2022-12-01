package com.comeremote.web.exception;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(Long id) {
        super("user with id " + id + " is already exists");
    }

}

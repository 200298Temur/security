package com.mohirdev.mohirdev.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String s) {
        super(s);
    }

    public UserNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }
}

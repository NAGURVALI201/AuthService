package com.social.authservice.exceptions;

public class UserNotSignedException extends RuntimeException{

    public UserNotSignedException(String message)
    {
        super(message);
    }
}

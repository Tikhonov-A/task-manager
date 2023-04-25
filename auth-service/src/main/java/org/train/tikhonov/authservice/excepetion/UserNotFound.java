package org.train.tikhonov.authservice.excepetion;

public class UserNotFound extends  RuntimeException {

    public UserNotFound(String message) {
        super(message);
    }
}

package org.train.tikhonov.authservice.exception;

public class JwtTokenIsExpired extends RuntimeException{

    public JwtTokenIsExpired(String message) {
        super(message);
    }
}

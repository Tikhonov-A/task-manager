package org.train.tikhonov.apigateway.exception;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String message) {
        super(message);
    }
}

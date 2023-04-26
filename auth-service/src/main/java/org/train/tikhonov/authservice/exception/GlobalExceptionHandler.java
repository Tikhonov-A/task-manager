package org.train.tikhonov.authservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({
            UsernameNotFoundException.class,
            InvalidEmailException.class,
            UsernameAlreadyExistsException.class,
            EmailException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseError handleUserNotFound(Exception e) {
        return new ResponseError(e.getMessage());
    }

}

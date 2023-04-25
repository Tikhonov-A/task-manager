package org.train.tikhonov.authservice.excepetion;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseError handleUserNotFound(UserNotFound exception) {
        return new ResponseError(exception.getMessage());
    }
}

package org.train.tikhonov.authservice.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class AuthenticationPointcuts {

    @Pointcut("execution(* org.train.tikhonov.authservice.service.AuthenticationService.auth*(..))")
    public void authMethod() { }
}

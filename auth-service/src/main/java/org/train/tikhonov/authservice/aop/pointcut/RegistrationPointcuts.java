package org.train.tikhonov.authservice.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class RegistrationPointcuts {

    @Pointcut("execution(* org.train.tikhonov.authservice.service.RegistrationService.register(..))")
    public void registerMethod() { }

    @Pointcut("execution(* org.train.tikhonov.authservice.service.RegistrationService.confirm(..))")
    public void confirmMethod() { }
}

package org.train.tikhonov.authservice.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.dto.AuthenticationDto;
import org.train.tikhonov.authservice.dto.RegistrationDto;


@Component
@Aspect
@Slf4j
public class RegistrationAspect {

    @Around("org.train.tikhonov.authservice.aop.pointcut.RegistrationPointcuts.registerMethod()")
    public Object aroundRegisterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RegistrationDto request = null;

        if (methodSignature.getName().equals("register")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof RegistrationDto) {
                    request = (RegistrationDto) arg;
                    log.info(
                            "Trying to register user with email {}",
                            request.email()
                    );
                }
            }
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.info(e.getMessage());
            throw e;
        }

        log.info("User was registered with email {} with unverified status",
                request.email()
        );
        return result;
    }

    @Around("org.train.tikhonov.authservice.aop.pointcut.RegistrationPointcuts.confirmMethod()")
    public void aroundConfirmMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String token = null;

        if (methodSignature.getName().equals("confirm")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof String) {
                    token = (String) arg;
                    log.info(
                            "Trying to confirm user with token {}",
                            token
                    );
                }
            }
        }

        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            log.info(e.getMessage());
            throw e;
        }

        log.info("User was verified with token {}", token);
    }
}

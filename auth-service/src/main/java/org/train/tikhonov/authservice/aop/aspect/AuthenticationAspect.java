package org.train.tikhonov.authservice.aop.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.dto.AuthenticationDto;


@Component
@Aspect
@Slf4j
public class AuthenticationAspect {


    @Around("org.train.tikhonov.authservice.aop.pointcut.AuthenticationPointcuts.authMethod()")
    public Object aroundAuthMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuthenticationDto request = null;

        if (methodSignature.getName().equals("authenticate")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof AuthenticationDto) {
                    request = (AuthenticationDto) arg;
                    log.info(
                            "Trying to authenticate user with email {}",
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

        log.info("User was authenticated with email {}",
                request.email()
        );
        return result;
    }
}

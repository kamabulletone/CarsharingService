package ru.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@Aspect
public class MyAspect {
    @Before("allServiceMethods()")
    public void logDate() {
        log.info("Date: {}", new Date());
    }
    @Pointcut("within(ru.services.*)")
    public void allServiceMethods() {}
}

package com.lwc.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Liu Wenchang
 */
//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.lwc.community.service.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }
}

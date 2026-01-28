package com.shop.ecommerce.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
//создает бин
@Component
@Order(2)
public class LoggingAspect {
	// Перед вызовом метода register() в UserService.
    @Before("execution(* com.shop.ecommerce.Service.UserService.register(..))")
    public void beforeSave() {
        System.out.println("1-метод register будет вызван ");
    }

    @Before("execution(* com.shop.ecommerce.Service.UserService.*(..))")
    public void beforeAllInUserService() {
        System.out.println("2-метод из userService был вызван ");
    }

    @After("execution(* com.shop.ecommerce.Service.UserService.register(..))")
    public void afterSave() {
        System.out.println("3-метод register завершился ");
    }
}

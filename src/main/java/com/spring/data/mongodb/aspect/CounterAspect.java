package com.spring.data.mongodb.aspect;

import io.micrometer.core.instrument.Counter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class CounterAspect {
    @Autowired
    Counter viewerRegistry;

    @Before("execution(* com.spring.data.mongodb.controller.AllEndPointController.*.get*(String))")
    public void getAllAdvice(){
        viewerRegistry.increment();
        System.out.println("Service method getter called");
    }
}

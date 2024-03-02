package com.example.DemoTest.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
public class Test0001Aspect {
	public static final Logger log = LoggerFactory.getLogger(Test0001Aspect.class);

	@Before("execution(* com.example.DemoTest.aspect.A0001Service.foo(*))")
	public void before(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println(Arrays.toString(args));
		log.info("before()");
	}
}

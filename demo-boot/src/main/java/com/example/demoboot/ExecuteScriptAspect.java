package com.example.demoboot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
public class ExecuteScriptAspect {

	/**
	 * AspectJ 切面注解中五种通知注解：
	 *  @Before: 前置通知, 在方法执行之前执行
	 *  @After: 后置通知, 在方法执行之后执行 。
	 *  @AfterRunning: 返回通知, 在方法返回结果之后执行
	 *  @AfterThrowing: 异常通知, 在方法抛出异常之后
	 *  @Around: 环绕通知, 围绕着方法执行
	 */


	/**
	 * 切点 2种写法
	 */
	// @Pointcut("execution(* sqy.aop_test.service.impl.AppiumServiceImpl.executeScript(..))")
	@Pointcut("@annotation(com.example.demoboot.Recording)")//自定义注解位置
	public void executeScript() {
	}

	/**
	 * 前置通知最先执行
	 */
	@Before("executeScript()")
	public void startRecording(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		System.out.println("前置通知，准备搞事情，准备一些工作==>洗锅");
	}

	//---------------3个后置 由下到上《顺序》执行 ---------------

	/**
	 * 后置通知, 在方法执行之后执行
	 */
	@Around("executeScript()")
	public void endRecording(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Parameter[] parameters = method.getParameters();

		// 假设只有一个参数
		String paramName = parameters[0].getName();
		Object paramValue = joinPoint.getArgs()[0];
		Object result = joinPoint.proceed(joinPoint.getArgs());

		System.out.println("参数值");
	}

	/**
	 * 后置通知, 在方法执行之后执行
	 */
	@After("executeScript()")
	public void pushFile() throws InterruptedException {
		Thread.sleep(2000);//休眠2秒
		System.out.println("后置通知02====>炒菜2秒");
	}

	/**
	 * 后置通知, 在方法执行之后执行
	 */
	@After("executeScript()")
	public void pushFile1() {
		System.out.println("后置通知03====>出锅");
	}

}

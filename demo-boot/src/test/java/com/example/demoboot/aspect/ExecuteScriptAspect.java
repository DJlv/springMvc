package com.example.demoboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
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
	@Pointcut("@annotation(com.example.demoboot.aspect.Recording)")//自定义注解位置
	public void executeScript() {
	}

	/**
	 * 前置通知最先执行
	 */
	@Before("executeScript()")
	public void startRecording(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println(args);
		System.out.println("前置通知，准备搞事情，准备一些工作==>洗锅");
	}

	//---------------3个后置 由下到上《顺序》执行 ---------------

	/**
	 * 后置通知, 在方法执行之后执行
	 */
	@After("executeScript()")
	public void endRecording() {
		System.out.println("后置通知01==>下油");
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

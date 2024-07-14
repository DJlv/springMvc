package com.example.test.m0005.t001;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class T001 {
	public static void main(String[] args) {
		AspectJExpressionPointcut pointcut  = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* foo())");
		MethodInterceptor adice = new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("before......");
				Object proceed = invocation.proceed();
				System.out.println("after......");
				return proceed;
			}
		};

		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, adice);

		Target2 target = new Target2();
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(target);

		factory.addAdvisor(advisor);
		factory.setInterfaces(target.getClass().getInterfaces());
		factory.setProxyTargetClass(false);

		Target2 proxy =  (Target2) factory.getProxy();

		System.out.println(proxy.getClass());
		proxy.foo();
		proxy.bar();
	}

	interface I1{
		void foo();
		void bar();
	}

	static class Target1 implements I1{

		@Override
		public void foo() {
			System.out.println("target1 foo");
		}

		@Override
		public void bar() {
			System.out.println("target1 bar");
		}
	}

	static class Target2{

		public void foo() {
			System.out.println("target2 foo");
		}

		public void bar() {
			System.out.println("target2 bar");
		}
	}
}

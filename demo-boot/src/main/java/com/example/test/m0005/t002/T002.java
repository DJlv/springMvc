package com.example.test.m0005.t002;

import com.example.test.m0005.t001.T001;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
public class T002 {

	static class Target2 {
		public void foo() {
			System.out.println("target2 foo");
		}

		public void bar() {
			System.out.println("target2 bar");
		}
	}



	public static void main(String[] args) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* foo())");

		MethodInterceptor adice = new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("before......");
				Object result = invocation.proceed();
				System.out.println("after......");
				return result;
			}
		};

		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, adice);


		Target2 target = new Target2();
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(target);
		factory.addAdvisor(advisor);
		factory.setInterfaces(target.getClass().getInterfaces());
		factory.setProxyTargetClass(false);

		Target2 proxy =(Target2) factory.getProxy();
		System.out.println(proxy.getClass());
		proxy.foo();
		proxy.bar();


	}
}

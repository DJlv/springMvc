package com.example.cgLibDemo;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib实现动态代理的原理
 * 2. 实现MethodInterceptor接口，用于在方法调用前后进行拦截和增强
 */
public class CustomInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// 在方法调用前进行拦截和增强
		System.out.println("Before method invocation");

		// 调用目标方法
		Object result = proxy.invokeSuper(obj, args);

		// 在方法调用后进行拦截和增强
		System.out.println("After method invocation");

		return result;
	}
}

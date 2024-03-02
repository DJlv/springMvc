package com.example.test.m0001;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] param) throws IOException {
		Target target = new Target();
		ClassLoader classLoader = Target.class.getClassLoader();
		Foo proxy = (Foo)  Proxy.newProxyInstance(classLoader, new Class[]{Foo.class},(p, method, args)->{
			System.out.println("before...");
			// 目标.方法(参数)
			// 方法.invoke(目标, 参数);
			Object result = method.invoke(target, args);
			System.out.println("after....");
			return result; // 让代理也返回目标方法执行的结果
		});

		System.out.println(proxy.getClass());

		proxy.foo();

		System.in.read();
	}
}

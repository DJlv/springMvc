package com.example.JdkDemo;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {

	interface Foo {
		void foo();
	}

	static class Target implements Foo {
		public void foo() {
			System.out.println("target foo");
		}
	}

	public static void main(String[] param) throws IOException {
		// 目标对象
		Target target = new Target();
		// 代理对象
		Foo proxy = (Foo) Proxy.newProxyInstance(
				Target.class.getClassLoader(), new Class[]{Foo.class},
				(p, method, args) -> {
					System.out.println("proxy before...");
					Object result = method.invoke(target, args);
					System.out.println("proxy after...");
					return result;
				});
		// 调用代理
		proxy.foo();
		System.out.println(proxy.getClass());
		System.in.read();
	}
}

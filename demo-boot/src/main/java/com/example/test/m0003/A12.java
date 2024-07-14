package com.example.test.m0003;

import com.example.test.m0001.Target;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;

public class A12 {
	interface Foo{
		void foo() throws Throwable;
		void bar() throws Throwable;
	}

	static class Target implements Foo{

		@Override
		public void foo() {
			System.out.println("foo");
		}

		@Override
		public void bar() {
			System.out.println("bar");
		}


		public static void main(String[] args) throws Throwable {
			Foo before = new $Proxy0(new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println("before");
					return method.invoke(new Target(), args);
				}
			});
			before.foo();
			before.bar();
		}
	}
}

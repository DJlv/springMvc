package com.example.test.m0003;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class $Proxy0 extends Proxy implements A12.Foo {

	static Method foo;
	static Method bar;

	static {
		try {
			foo = A12.Foo.class.getMethod("foo");
			bar = A12.Foo.class.getMethod("bar");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	protected $Proxy0(InvocationHandler h) {
		super(h);
	}

	@Override
	public void foo() throws Throwable {
		h.invoke(this, foo, new Object[0]);
	}

	@Override
	public void bar() throws Throwable {
		h.invoke(this, bar, new Object[0]);
	}
}

package com.example.test.m0004.t001;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class M004Main {
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		Target target = new Target();
		proxy.setMethodInterceptor(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("before");
				method.invoke(target,args);
				return null;
			}
		});
		proxy.save();
		proxy.save(1);
		proxy.save(2L);
	}
}

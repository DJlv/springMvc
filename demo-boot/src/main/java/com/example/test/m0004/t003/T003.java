package com.example.test.m0004.t003;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class T003 {
	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		Target target = new Target();
		proxy.setMethodInterceptor(new MethodInterceptor() {
			@Override
			public Object intercept(Object p, Method method, Object[] args, MethodProxy proxy) throws Throwable {

				System.out.println("before......");
//				return proxy.invoke(target,args);
				return proxy.invokeSuper(p,args);
			}
		});
		proxy.save();
		proxy.save(1);
		proxy.save(2l);
	}
}

package com.example.test.m0004.t003;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class Proxy extends Target{


	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	private MethodInterceptor methodInterceptor;


	static Method save0;
	static Method save1;
	static Method save2;
	static MethodProxy save0Super;
	static MethodProxy save1Super;
	static MethodProxy save2Super;

	static {
		try {
			save0 = com.example.test.m0004.t002.Target.class.getMethod("save");
			save1 = com.example.test.m0004.t002.Target.class.getMethod("save",int.class);
			save2 = com.example.test.m0004.t002.Target.class.getMethod("save",long.class);
			save0Super = MethodProxy.create(Target.class, Proxy.class,"()V","save","saveSuper");
			save1Super = MethodProxy.create(Target.class, Proxy.class,"(I)V","save","saveSuper");
			save2Super = MethodProxy.create(Target.class, Proxy.class,"(J)V","save","saveSuper");
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodError(e.getMessage());
		}
	}

	public void saveSuper(){
		super.save();
	}
	public void saveSuper(int i){
		super.save(i);
	}
	public void saveSuper(long j){
		super.save(j);
	}
	@Override
	public void save() {
		try {
			methodInterceptor.intercept(this,save0,new Object[0],save0Super);
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	@Override
	public void save(int i) {
		try {
			methodInterceptor.intercept(this,save1,new Object[]{i},save1Super);
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	@Override
	public void save(long j) {
		try {
			methodInterceptor.intercept(this,save2,new Object[]{j},save2Super);
		} catch (Throwable e) {
			throw new UndeclaredThrowableException(e);
		}
	}
}

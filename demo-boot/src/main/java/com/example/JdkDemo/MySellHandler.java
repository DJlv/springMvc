package com.example.JdkDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理的开发步骤
 * 3. 创建InvocationHandler 接口的实现类，在invoke 方法中实现代理对象的功能
 */
public class MySellHandler implements InvocationHandler {
	private Object target = null;

	//动态代理，目标对象是动态的，是由你自己传入的，传入的对象是谁，就给谁创建代理
	public MySellHandler(Object target) {
		this.target = target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object res = null;
		//1.执行目标方法
		//向厂家发送订单，厂家发货
		res = method.invoke(target,args); //厂家的价格

		//2.功能增强
		//商家（代理）需要加价，并且赠送买家优惠券
		if (res !=null){
			Float price = (Float) res;
			price = price +30;
			res = price;
		}

		System.out.println("商家赠送了一张五元优惠券");

		//商家加价后的新价格
		return res;

	}
}

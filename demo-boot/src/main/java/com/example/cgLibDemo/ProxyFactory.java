package com.example.cgLibDemo;

import org.springframework.cglib.proxy.Enhancer;

/**
 *cglib实现动态代理的原理
 * 3. 使用Enhancer类创建代理对象，并设置目标类和方法拦截器。
 */
public class ProxyFactory {
	public static Object getProxyInstance(Class<?> targetClass) {
		// 创建代理对象
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(new CustomInterceptor());
		return enhancer.create();
	}

	/**
	 * cglib实现动态代理的原理
	 * 4. 创建代理对象，并调用目标方法
	 * @param args
	 */
	public static void main(String[] args) {
		TargetClass target = (TargetClass) ProxyFactory.getProxyInstance(TargetClass.class);
		target.doSomething();
	}
}

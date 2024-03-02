package com.example.cgLibDemo;

/**
 * cglib实现动态代理的原理
 * 1. 创建目标类：定义一个需要被代理的目标类。
 */
public class TargetClass {
	public void doSomething() {
		System.out.println("Doing something...");
	}
}

package com.example.JdkDemo;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理的开发步骤
 * 4.使用Proxy类的静态方法，创建代理对象，并把返回值转为接口类型
 */
public class MainShop {
	public static void main(String[] args) {
		//创建代理对象，使用Proxy
		//1.创建目标类对象
		UsbKingFactory usbKingFactory = new UsbKingFactory();

		//2.创建InvocationHandler对象
		MySellHandler handler = new MySellHandler(usbKingFactory);

		//3.创建代理对象
		UsbSell proxy =
				(UsbSell) Proxy.newProxyInstance(
						usbKingFactory.getClass().getClassLoader(),
						usbKingFactory.getClass().getInterfaces(),
						handler);
		float price = proxy.sell(1);
		System.out.println("通过代理对象调用了目标方法（商家向厂家发送了订单）："+price);
	}
}

package com.example.JdkDemo;

/**
 * JDK动态代理的开发步骤
 * 2. 创建目标类实现接口
 */
public class UsbKingFactory implements UsbSell {
	@Override
	public float sell(int amount) {
		System.out.println("目标类执行了sell目标方法！");
		return 65.0f; //u盘的厂家价格
	}
}

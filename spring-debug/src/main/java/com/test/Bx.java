package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bx {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("tx.xml");
		com.test.A bean = context.getBean(com.test.A.class);
		System.out.println(bean.getApplicationContext());
		System.out.println(bean);
	}
}

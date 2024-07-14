package com.example.test.m0004.t003;

import org.springframework.cglib.core.Signature;

public class ProxyFastClassMain {
	public static void main(String[] args) {
		ProxyFastClass fastClass = new ProxyFastClass();
		int index = fastClass.getIndex(new Signature("saveSuper","()V"));
		System.out.println(index);
		fastClass.invoke(index,new Proxy(),new Object[0]);
	}
}

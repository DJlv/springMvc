package com.example.demoboot.m0007.test001;


import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

public class YmlReturnValueHandler{
	public static void main(String[] args) {
		// 仅有类型转换的功能
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
		Integer number = typeConverter.convertIfNecessary("13", int.class);
		Date date = typeConverter.convertIfNecessary("1999/03/04", Date.class);
		System.out.println(number);
		System.out.println(date);
	}
}

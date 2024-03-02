package com.example.demoboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.SimpleTypeConverter;

import java.util.Date;

public class Test001 {
	@Test
	public void typeTest() {
		SimpleTypeConverter typeConverter = new SimpleTypeConverter();
		Date date = typeConverter.convertIfNecessary("2023/10/01", Date.class);
		System.out.println(date);
	}
}

package com.example.demoboot.aspect;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAspact {
	@Autowired
	private Test0v2 test0v2;
	@Test
	public void less() {
		test0v2.set();
		System.out.println(222222);
	}
}

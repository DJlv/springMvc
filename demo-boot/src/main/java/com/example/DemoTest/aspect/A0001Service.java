package com.example.DemoTest.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class A0001Service {

	public static final Logger log = LoggerFactory.getLogger(A0001Service.class);

	public void foo(String a) {
		log.info("foo()----{}",a);
	}
}

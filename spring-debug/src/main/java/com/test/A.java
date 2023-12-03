package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class A implements ApplicationContextAware {

	private String name;
	private ApplicationContext applicationContext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}

package com.example;

import com.example.DemoTest.aspect.A0001Service;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.lang.reflect.Field;

@SpringBootApplication
public class DemoBootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoBootApplication.class, args);
		A0001Service bean = context.getBean(A0001Service.class);
		System.out.println(bean.getClass());
		bean.foo("cccccc");
		context.close();
	}
}

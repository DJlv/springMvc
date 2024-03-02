package com.example.demoboot;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Listernal {
	@EventListener
	public void aaa (DemoBootApplicationTests.User user) {
		System.out.println("User----------");
		System.out.println(user.getName());
	}

	@EventListener
	public void aaa (DemoBootApplicationTests.Student student) {
		System.out.println("Student----------");
		System.out.println(student.getName());
	}
}

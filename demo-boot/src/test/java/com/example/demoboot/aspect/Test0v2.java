package com.example.demoboot.aspect;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Test0v2 {

	@Recording
	@PostMapping("/test")
	public void set() {
		System.out.println("cccccccc");
	}
}

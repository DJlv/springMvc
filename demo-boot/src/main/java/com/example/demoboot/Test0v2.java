package com.example.demoboot;


import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/all")
public class Test0v2 {

	@Recording
	@PostMapping("/test")
	public void set(@RequestParam("id") String id) {
		System.out.println("cccccccc");
	}
}

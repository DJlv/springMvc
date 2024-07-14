package org.springframework;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigAll {

	@Bean
	public MyApp getMyApp() {
		return new MyApp();
	}
}

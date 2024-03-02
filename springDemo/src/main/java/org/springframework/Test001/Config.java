package org.springframework.Test001;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public  class Config {
	@Bean
	public Bean1 bean1() {
		return new Bean1();
	}

	@Bean
	public Bean2 bean2(Bean1 bean1) {
		Bean2 bean2 = new Bean2();
		bean2.setBean1(bean1);
		return bean2;
	}
}
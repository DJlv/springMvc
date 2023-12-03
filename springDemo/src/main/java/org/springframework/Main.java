package org.springframework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Config.class);

		for (String name : context.getBeanDefinitionNames()) {
			System.out.println(name);
		}

		System.out.println(context.getBean(Bean2.class).getBean1());
	}

	@Configuration
	static class Config {
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

	static class Bean1 {
	}

	static class Bean2 {

		private Bean1 bean1;

		public void setBean1(Bean1 bean1) {
			this.bean1 = bean1;
		}

		public Bean1 getBean1() {
			return bean1;
		}
	}
}
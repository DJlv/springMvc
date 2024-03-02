package com.example.demoboot;

import org.junit.jupiter.api.Test;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Map;


@SpringBootTest
class DemoBootApplicationTests {


	@Autowired
	private Environment environment;

	/**
	 * 测试 Environment
	 */
	@Test
	public void environment_test() {
		System.out.println("测试---------------------environment---------------------------------");
		System.out.println(environment.getProperty("server.port"));
	}

	@Autowired
	private ResourcePatternResolver resource;

	/**
	 * 测试 ResourcePatternResolver
	 */
	@Test
	public void resource_test() {
		System.out.println("测试---------------------resource---------------------------------");
		try {
			Resource[] resources = resource.getResources("classpath*:META-INF/spring.factories");
			for (Resource resource : resources) {
				System.out.println(resource);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	class User {
		private String name;

		public User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	class Student {
		private String name;

		public Student(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 测试 ApplicationEventPublisher
	 */
	@Test
	public void applicationEventPublisher_test() {
		System.out.println("测试---------------------ApplicationEventPublisher---------------------------------");
		User user = new User("user---zr");
		applicationEventPublisher.publishEvent(user);
	}

	/**
	 * 测试 ApplicationEventPublisher
	 */
	@Test
	public void applicationEventPublisherName_test() {
		System.out.println("测试---------------------ApplicationEventPublisher---------------------------------");
		Student student = new Student("student--zr");
		applicationEventPublisher.publishEvent(student);
	}



	static class Config {
		@Bean
		public Bean1 bean1() {
			return new Bean1();
		}
	}

	static class Bean1 {
		private static final Logger log = LoggerFactory.getLogger(Bean1.class);

		public Bean1() {
			log.debug("构造Bean1");
		}
	}

	@Test
	public void DefaultListableBeanFactoryTest() {
		System.out.println("测试---------------------DefaultListableBeanFactory---------------------------------");

		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("signleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("config", beanDefinition);


		/**
		 * 添加后处理器
		 */
		AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);


		/**
		 * 获取Bean后处理器
		 * 		主要功能 补充了一些bean定义
		 * 并运行
		 */
		beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
					/**
					 * 执行后处理器
					 */
					beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
				}
		);

		beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}
	}


	@Test
	public void GenericApplicationContextTest001() {
		GenericApplicationContext context = new GenericApplicationContext();
		context.refresh();
	}

}

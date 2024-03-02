package org.springframework.Test001;

import org.springframework.Test001.Bean1;
import org.springframework.Test001.Config;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;

public class TestBeanFactory {
	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		/**  添加配置类单例*/
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope(BeanDefinition.SCOPE_SINGLETON).getBeanDefinition();
		beanFactory.registerBeanDefinition("config", beanDefinition);

		/** 给BeanFactory添加一些常用的后处理器*/
		AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);


		/** Bean里面的后处理解析的Bean
		 *  BeanFactory后处理器主要功能，不补充了Bean的一些定义
		 * */
		beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		});


		/**
		 * Bean后处理器，针对Bean生命周期各个阶段提供扩展
		 * */

		/** 建立BeanFactory工厂与Bean后处理器之间的联系*/
		beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream().sorted(beanFactory.getDependencyComparator()).forEach(beanFactory::addBeanPostProcessor);
		/**预先初始化Bean单例*/
		beanFactory.preInstantiateSingletons();
		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}


		System.out.println(beanFactory.getBean(Bean1.class).getBean2());

	}
}

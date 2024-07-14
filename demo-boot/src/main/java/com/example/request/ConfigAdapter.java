package com.example.request;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ConfigAdapter implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new TestArgument_value());
//		//当然可以增加一连串的解析器
//		argumentResolvers.add(new FoodInfoResolver());
	}
}

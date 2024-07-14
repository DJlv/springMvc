package com.example.boot.controller;

import com.example.request.UserId;
import com.example.request.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Controller
public class TestBoot {



	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	private static final Logger log = LoggerFactory.getLogger(TestBoot.class);

	@GetMapping("/test1")
	public ModelAndView test2(@UserId String userInfo) throws Exception {
		log.debug("test1()");
		// 获取映射结果
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		handlerMethods.forEach((k, v) -> {
			System.out.println(k + "=" + v);
		});
		return null;
	}
}

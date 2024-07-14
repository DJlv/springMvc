package com.example.demoboot.m0007.test001;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
public class MT001 {
	public static void main(String[] args) throws Exception {
		AnnotationConfigServletWebServerApplicationContext context =
				new AnnotationConfigServletWebServerApplicationContext(WebConfigs.class);

		// 作用 解析 @RequestMapping 以及派生注解，生成路径与控制器方法的映射关系, 在初始化时就生成
		RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

		// 获取映射结果
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
		handlerMethods.forEach((k, v) -> {
			System.out.println(k + "=" + v);
		});

		// 请求来了，获取控制器方法  返回处理器执行链对象
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test1");
//		request.setParameter("name", "张三");
//		request.addHeader("token", "某个令牌");
		MockHttpServletResponse response = new MockHttpServletResponse();
		HandlerExecutionChain chain = handlerMapping.getHandler(request);
		System.out.println(chain);

		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		// HandlerAdapter 作用: 调用控制器方法
		MyRequestMappingHandlerAdapter handlerAdapter = context.getBean(MyRequestMappingHandlerAdapter.class);
        assert chain != null;
        handlerAdapter.invokeHandlerMethod(request, response, (HandlerMethod) chain.getHandler());
	}
}

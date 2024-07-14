package com.example.mock.mockBoot;

import org.springframework.mock.web.MockHttpServletRequest;

public class MockBoot {
	public static void main(String[] args) {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test1");
		request.setParameter("name", "张三");
		request.addHeader("token", "某个令牌");
	}
}

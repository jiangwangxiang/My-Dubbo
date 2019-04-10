package com.fanghao.service.impl;

import com.fanghao.service.HelloService;

public class HelloServiceImpl implements HelloService {
	@Override
	public String sayHello(String username) {
		System.out.println("hello " + username);
        return "Hello:"+username;
	}
}

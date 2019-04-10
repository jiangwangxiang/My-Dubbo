package com.fanghao.consumer;

import com.fanghao.framework.ProxyFactory;
import com.fanghao.service.HelloService;

public class Consumer {
	public static void main(String[] args) {
		//此处模拟spring的bean容器
		HelloService service = ProxyFactory.getProxy(HelloService.class);
		String result = service.sayHello("zhangsan");
        System.out.println(result);
	}
}

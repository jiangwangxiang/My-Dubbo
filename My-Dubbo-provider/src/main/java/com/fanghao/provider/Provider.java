package com.fanghao.provider;

import com.fanghao.framework.URL;
import com.fanghao.protocol.http.HttpServer;
import com.fanghao.register.Register;
import com.fanghao.service.HelloService;
import com.fanghao.service.impl.HelloServiceImpl;

public class Provider {
	public static void main(String[] args) {
		//注册服务
		URL url = new URL("localhost", 8080);
		Register.regist(url, HelloService.class.getName(), HelloServiceImpl.class);
		
		//启动http服务端监听服务
		HttpServer httpServer = new HttpServer(url.getHostname(),url.getPort());
		httpServer.start();
	}
}

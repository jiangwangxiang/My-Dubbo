package com.fanghao.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.fanghao.protocol.http.HttpClient;
import com.fanghao.register.Register;

//代理工厂类，服务消费端生成一个服务的远程提供者代理
public class ProxyFactory<T> {
	public static <T> T getProxy(Class interfaceClass) {
		return (T)Proxy.newProxyInstance(
				interfaceClass.getClassLoader(), 
				new Class[] {interfaceClass}, 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//构造远程服务调用信息
						Invocation invocation = new Invocation(
								interfaceClass.getName(), 
								method.getName(), 
								new Class[] {String.class}, 
								args);
						
						//模拟负载均衡，随机获取服务提供者
						URL url = Register.random(interfaceClass.getName());
						if(url == null) {
							throw new RuntimeException("没有对应的服务提供者！");
						}
						
						//发起远程调用
						HttpClient httpClient = new HttpClient();
						return httpClient.post(url.getHostname(), url.getPort(), invocation);
					}
				});
	}
}

package com.fanghao.protocol.http;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanghao.framework.Invocation;
import com.fanghao.framework.URL;
import com.fanghao.register.Register;

@SuppressWarnings("unchecked")
public class HttpServerHandler {
	public void handler(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// http请求流转为对象 -- 解码过程
			InputStream is = req.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Invocation invocation = (Invocation)ois.readObject();
			
			//寻找远程调用信息对应的本地实现类，通过反射执行
			Class implClass = Register.get(new URL("localhost", 8080), invocation.getInterfaceName());
			Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
			
			//反射调用，获得调用结果
			String result = (String) method.invoke(implClass.newInstance(), invocation.getParams());
			// 调用结果写入到http响应流中
			resp.getOutputStream().write(result.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

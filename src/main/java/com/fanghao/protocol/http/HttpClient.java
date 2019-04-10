package com.fanghao.protocol.http;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fanghao.framework.Invocation;

//http协议的客户端
public class HttpClient {
	//远程方法调用，将远程服务调用的调用信息通过网络传输给服务提供者
	public String post(String hostname, Integer port, Invocation invocation) {
		try {
			//进行http连接
			URL url = new URL("http", hostname, port, "/client/");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			//将远程服务调用的对象写入输出流
			OutputStream os = connection.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(invocation);
			oos.flush();
			oos.close();
			
			//将输入流转为字符串（此处也可以是java对象）
			InputStream is = connection.getInputStream();
			byte[] result = new byte[1024];
			is.read(result);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}

package com.fanghao.protocol.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5747871915617841223L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
		// 方便后期在此拓展服务
		new HttpServerHandler().handler(req, resp);
	}; 
}

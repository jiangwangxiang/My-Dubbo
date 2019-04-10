package com.fanghao.framework;

import java.io.Serializable;

//远程服务调用的调用信息(序列化成流在网络上传输)
public class Invocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7900566393646142253L;
	
	//接口名
	private String interfaceName;
	//方法名
	private String methodName;
	//参数类型列表
	private Class[] paramTypes;
	//参数值列表
	private Object[] params;
	
	public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
		this.params = params;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}

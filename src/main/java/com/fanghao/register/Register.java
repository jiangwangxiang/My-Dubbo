package com.fanghao.register;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.fanghao.framework.URL;

//注册器
public class Register {
	private static final String FILE_PATH = "F://MyDubbo//register.txt";
	//服务提供者映射 接口名-<服务提供者地址-服务实现类>

	//注册服务（暴露接口）
	public static void regist(URL url, String interfaceName, Class implClass) {
		Map<String, Map<URL, Class>> registeredServices = getFile();
		if(registeredServices == null) {
			registeredServices = new HashMap<String, Map<URL, Class>>();
		}
		Map<URL, Class> map = registeredServices.get(interfaceName);
		if(map == null){
			map = new HashMap<URL, Class>();
			registeredServices.put(interfaceName, map);
		}	
		map.put(url, implClass);
		
		//写入文本，模拟注册到zookeeper
		saveFile(registeredServices);
		
		
		/***********服务提供者列表，暂时这样实现，需要修改时可删除*************/
		Map<String, List<URL>> providersList = getProvidersFile();
		if(providersList == null) {
			providersList = new HashMap<>();
		}
		List<URL> urlList = providersList.get(interfaceName);
		if(urlList == null) {
			urlList = new ArrayList<>();
			providersList.put(interfaceName, urlList);
		}
		urlList.add(url);
		saveProvidersFile(providersList);
		/***********服务提供者列表，暂时这样实现，需要修改时可删除*************/
	}
	
	/***********服务提供者列表，暂时这样实现，需要修改时可删除*************/
	public static void saveProvidersFile(Map<String, List<URL>> providersInfo) {
		File file = createIfNotExist("F://MyDubbo//providers.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(providersInfo);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Map<String, List<URL>> getProvidersFile(){
		File file = createIfNotExist("F://MyDubbo//providers.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return (Map<String, List<URL>>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//模拟负载均衡，随机获取服务提供者
	public static URL random(String interfaceName) {
		Map<String, List<URL>> providersList = getProvidersFile();
		if(providersList != null) {
			List<URL> urlList = providersList.get(interfaceName);
			if(urlList != null) {
				Random random = new Random();
				int providerNum = random.nextInt(urlList.size());
				return urlList.get(providerNum);
			}
			return null;
		}
		return null;
	}
	/***********服务提供者列表，暂时这样实现，需要修改时可删除*************/
	
	//从注册中心获取实现类（发现服务）
	public static Class get(URL url, String interfaceName) {
		return getFile().get(interfaceName).get(url);
	}
	
	//写入文本
	public static void saveFile(Map<String, Map<URL, Class>> serviceInfo) {
		File file = createIfNotExist(FILE_PATH);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(serviceInfo);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取文本
	public static Map<String, Map<URL, Class>> getFile(){
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return (Map<String, Map<URL, Class>>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static File createIfNotExist(String filePath){
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	public static void main(String[] args) {
		regist(new URL("127.0.0.422", 8328), "demoService", String.class);
		regist(new URL("127.0.0.231", 4577), "demoService", Integer.class);
		regist(new URL("127.56.4.422", 3653), "demoService", List.class);
		regist(new URL("217.22.0.231", 112), "demoService", Map.class);
		
		Map<String, Map<URL, Class>> file = getFile();
		Iterator<Entry<String, Map<URL, Class>>> iterator = file.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, Map<URL, Class>> next = iterator.next();
			System.out.println(next.getKey());
			Map<URL, Class> inMap = next.getValue();
			System.out.println(inMap.size());
			Iterator<Entry<URL, Class>> inIt = inMap.entrySet().iterator();
			while(inIt.hasNext()) {
				Entry<URL, Class> inNext = inIt.next();
				System.out.println(inNext.getKey().getHostname() + ":" + inNext.getKey().getPort());
				System.out.println(inNext.getValue().getName());
			}
		}
		
		System.out.println("--------------------");
		
		URL random = random("demoService");
		System.out.println(random.getHostname() + ":" + random.getPort());
		random = random("demoService");
		System.out.println(random.getHostname() + ":" + random.getPort());
		random = random("demoService");
		System.out.println(random.getHostname() + ":" + random.getPort());
		random = random("demoService");
		System.out.println(random.getHostname() + ":" + random.getPort());
	}
	
}

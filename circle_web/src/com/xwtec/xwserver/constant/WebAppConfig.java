package com.xwtec.xwserver.constant;

import java.util.Properties;

import org.springframework.context.ApplicationContext;

public class WebAppConfig {

	/**
	 * 存储全局的properties属性文件配置信息(延迟初始化)
	 */
	public static final Properties GLOBAL_CONFIG_PROPERTIES = new Properties();
	
	/**
	 * web应用程序真实根路径(延迟初始化)
	 */
	public static String APP_ROOT_PATH = null;
	
	/**
	 * spring上下文(延迟初始化)
	 */
	public static ApplicationContext SPRING_APPLICATION_CONTEXT = null;
	
	/**
	 * web应用程序的上下文路径,如：/mall(延迟初始化)
	 */
	public static String CONTEXT_PATH = null;
	
	public static String DEFAULT_ENCODING = "UTF-8";
	
	public static String FILE_SEPARATOR = System.getProperty("file.separator");
	
}

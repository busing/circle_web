package com.xwtec.xwserver.util;


/**
 * 线程安全的数据源名称全局变量
 * <p>
 * Copyright: Copyright (c) 2013-11-15 上午10:48:12
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liuwenbing@xwtec.cn
 * @version 1.0.0
 */
public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();

	public static void switchTo(String dataSourceName) {
		dataSourceHolder.set(dataSourceName);
	}

	public static String getCurrentDataSource() {
		return dataSourceHolder.get();
	}

	public static void clear() {
		dataSourceHolder.remove();
	}
}

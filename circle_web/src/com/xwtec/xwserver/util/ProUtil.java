package com.xwtec.xwserver.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.xwtec.xwserver.constant.ConstantKeys;

/**
 * properties文件操作相关. <br>
 * WEB服务启动后自动加载的properties文件中的KEY-VALUE，在本类中可以获取到并向外提供接口取值.
 * <p>
 * Copyright: Copyright (c) 2013-11-21 上午11:15:03
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ProUtil extends PropertyPlaceholderConfigurer {
	
	private static Properties properties;
	
	/**
	 * 方法描述:重写PropertyPlaceholderConfigurer中processProperties方法，执行原父类方法的同时将所有加载的properties中数据存到本类中的properties变量中
	 * date:2013-11-21
	 * add by: liu_tao@xwtec.cn
	 */
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		properties = props;
	}
	
	/**
	 * 方法描述:取properties文件中数据，根据KEY值取VALUE，支持value为中文，返回为UTF-8编码
	 * @param key properties文件中配置的key值
	 * @return 对应的value值
	 * date:2013-11-21
	 * add by: liu_tao@xwtec.cn
	 */
	public static String get(String key) {
		String result;
		try {
			result = new String(properties.getProperty(key).getBytes(ConstantKeys.WebKey.CHARACTER_ENCODING_ISO_8859_1), ConstantKeys.WebKey.CHARACTER_ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			result = properties.getProperty(key);
		}
		return result;
	}
}
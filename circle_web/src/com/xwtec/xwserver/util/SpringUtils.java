package com.xwtec.xwserver.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * Spring ApplicationContext 工具类
 * 
 * <p>Description: </p>
 * <p>Copyright	 : Copyright (c) 2012 </p>
 * <p>Company	 : xwtec.com </p>
 * <p>Version	 : 1.0 </p>
 * @author	     : pengpeng
 * @date 	     : 2012-11-22 上午09:24:09
 */
@SuppressWarnings("unchecked")
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> baseType){
		return applicationContext.getBeansOfType(baseType);
	}
	
}

package com.xwtec.xwserver.util.database;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.xwtec.xwserver.constant.ConstantBusiKeys;

public class CustomPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory,
			java.util.Properties props) throws BeansException {

		super.processProperties(beanFactory, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ConstantBusiKeys.GLOBAL_CONFIG_PROPERTIES.put(keyStr, value);
		}
	}
}

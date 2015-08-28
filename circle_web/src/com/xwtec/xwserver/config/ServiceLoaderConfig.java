package com.xwtec.xwserver.config;

import org.apache.log4j.Logger;

import com.circle.service.dct.IDictService;
import com.xwtec.xwserver.util.SpringUtils;


/**
 * 启动工程需要调取的业务加载类,默认执行loaderServices方法<br>
 * <p>
 * Copyright: Copyright (c) Jun 6, 2013 5:35:39 PM
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author houxu@xwtec.cn
 * @version 1.0.0
 */
public class ServiceLoaderConfig {
	
	/**
	 * logger 加载日志
	 */
	private static final Logger logger = Logger.getLogger(ServiceLoaderConfig.class);

	/**
	 * 方法描述:注册方法,自定义启动的方法需要定义在该方法内调取
	 * date:Jun 6, 2013
	 * add by: houxu@xwtec.cn
	 */
	public void loaderServices(){
		//将城市和areacode注入静态map强制刷新
		this.loadCitiesNameAndAreacodeMapData();
	}
	
	/**
	 * 方法描述:将城市和areacode注入静态map强制刷新<br>
	 * date:Jun 6, 2013
	 * add by: houxu@xwtec.cn
	 */
	private void loadCitiesNameAndAreacodeMapData(){
		logger.info("get into ServiceLoaderConfig.loadCitiesNameAndAreacodeMapData");
		try {
			IDictService dictService = SpringUtils.getBean("dictService");
			dictService.initDictMap();
			dictService.initGoodType();
			dictService.initGoodTypeArg();
			dictService.initGoodTypeAttr();
			dictService.initFarm();
			dictService.initMessageTypeBeans();
			logger.info("ServiceLoaderConfig.loadCitiesNameAndAreacodeMapData had completed successfully.");
        }catch (Exception e) {
			logger.error("ServiceLoaderConfig.loadCitiesNameAndAreacodeMapData had completed failed.");
        	logger.error("[ServiceLoaderConfig:doAction]:failed.ex message:"+e.getMessage());
        }
	}
}

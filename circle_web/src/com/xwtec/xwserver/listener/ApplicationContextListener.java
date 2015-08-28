package com.xwtec.xwserver.listener;

import java.lang.reflect.Method;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xwtec.xwserver.constant.WebAppConfig;
import com.xwtec.xwserver.util.ExceptionUtil;
import com.xwtec.xwserver.util.SpringUtils;

/**
 * 
 * 全局监听,初始化系统. <br>
 * <p>
 * Copyright: Copyright (c) 2014-12-13 下午09:18:42
 * <p>
 * <p>
 * @author wangfengtong@xwtec.cn
 * @version 1.0.0
 */
public class ApplicationContextListener implements ServletContextListener
{
	private static WebApplicationContext webApplicationContext;
	
	private static SpringUtils helper = new SpringUtils();
	 
	private static Log logger;
	
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		logger.info("圈子服务关闭!");
	}

	public void contextInitialized(ServletContextEvent context)
	{
		logger = LogFactory.getLog(ApplicationContextListener.class);
		logger.info("\n圈子服务启动......");
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.info("当前环境参数:");
		logger.info("       操作系统名称 : " + System.getProperty("os.name"));
		logger.info("       操作系统版本 : " + System.getProperty("os.version"));
		logger.info("      JAVA 运行时环境版本 : " + System.getProperty("java.version"));
		logger.info("      JAVA 虚拟机实现版本 : " + System.getProperty("java.vm.specification.version"));
		logger.info("      JAVA 安装目录 : " + System.getProperty("java.home"));
		logger.info("       文件分隔符 : " + System.getProperty("file.separator"));
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
		
		try {
			/** 初始化系统 */
			String realPath = context.getServletContext().getRealPath("");
			if (!realPath.endsWith("/")) {
				realPath += "/";
			}
			WebAppConfig.APP_ROOT_PATH = realPath;
			WebAppConfig.CONTEXT_PATH = context.getServletContext().getContextPath();
			WebAppConfig.SPRING_APPLICATION_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
			WebAppConfig.FILE_SEPARATOR = System.getProperty("file.separator");
			
		} 
		catch (Exception e) 
		{
			logger.error("系统启动失败,初始化参数异常 : " + ExceptionUtil.ExceptionToString(e));
			System.exit(-1);
		}
		try{
			logger.info("Business Loader starting ...");
			webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context.getServletContext());  
	        helper.setApplicationContext(webApplicationContext);
	        
			//自定义启动加载方法
			Class<?> clazz = Class.forName("com.xwtec.xwserver.config.ServiceLoaderConfig");
			Object obj = clazz.newInstance();
			Method rM = obj.getClass().getDeclaredMethod("loaderServices");
			rM.invoke(obj);
			//IActionService actionService = 
//			IGoodTypeService goodTypeService = SpringUtils.getBean("goodTypeService");
//			goodTypeService.initDictMap();
			logger.info("Business Loader has started successful!");
		}catch (Exception ex) {
			logger.error("Business Loader has started failed! System started Exception.");
			logger.error("failed message ex: " + ExceptionUtil.ExceptionToString(ex));
			logger.error(ex.getMessage());
		}
	}
	
}
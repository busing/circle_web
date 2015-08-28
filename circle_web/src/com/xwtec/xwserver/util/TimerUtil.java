package com.xwtec.xwserver.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * 定时器相关. <br>
 * 指定一个继承了TimerTask的任务类，在指定的时间触发.
 * <p>
 * Copyright: Copyright (c) 2013-12-5 下午3:22:01
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class TimerUtil {
	
	private static final Logger log = Logger.getLogger(TimerUtil.class);
	
	/**
	 * 方法描述:
	 * @param clazz 继承TimerTask的类class
	 * @param date 指定执行的时间
	 * date:2013-12-5
	 * add by: liu_tao@xwtec.cn
	 */
	public static void schedule(Class<? extends TimerTask> clazz,Date date) {
		Timer timer = new Timer();
		try {
			timer.schedule(clazz.newInstance(), date);
		} catch (InstantiationException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		} catch (IllegalAccessException e) {
			log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
		}
	}
}
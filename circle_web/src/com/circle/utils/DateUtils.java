package com.circle.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015年1月24日 下午1:35:35
 * <p>
 * Company: Taiyuan
 * <p>
 * @author Taiyuan
 * @version 1.0.0
 */
public class DateUtils {

	/**
	 * 方法描述:获取订单结束时间
	 * @param 
	 * @return 
	 * date:2015年1月24日
	 * @author Taiyuan
	 */
	public static String getEndTime(String issue_time)
	{
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(issue_time);
			Calendar cal= Calendar.getInstance();
			cal.set(Calendar.DATE, date.getDate()-1);
			cal.set(Calendar.HOUR_OF_DAY, 17);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			return  sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
}

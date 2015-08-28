
/**   
 * @Title: SystemExceptionUtil.java 
 * @Package com.circle.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author Taiyuan
 * @date 2014年12月28日 上午10:13:42 
 * @version V1.0   
 */ 
    
package com.circle.utils;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.constant.ConstantBusiKeys.ResultInfoKey;
import com.xwtec.xwserver.exception.SPTException;
import com.xwtec.xwserver.util.CommonUtil;


/** 
 * @ClassName: SystemExceptionUtil 
 * @Description: TODO(系統异常消息处理，controller) 
 * @author Taiyuan
 * @date 2014年12月28日 上午10:13:42 
 */

public class SystemExceptionUtil {
	
	
	/**  
	 * 此方法描述的是：  controller sptexception统一处理方法
	 * @author: Taiyuan  
	 * @version: 2014年12月28日 上午10:17:09  
	 */  
	public static void logSPTException(ModelAndView mav,Logger logger,SPTException e)
	{
		mav.addObject(ResultInfoKey.MSG, "页面跳转失败，请联系管理员！");
		mav.addObject(ResultInfoKey.URL, "/login/login.action");
		mav.addObject(ResultInfoKey.ERROR, e);
		mav.setViewName(ResultInfoKey.RESULT_INFO_PATH);
		logger.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
	}
	
}

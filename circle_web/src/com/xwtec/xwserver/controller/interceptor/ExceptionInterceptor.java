package com.xwtec.xwserver.controller.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.constant.ConstantKeys;
import com.xwtec.xwserver.util.CommonUtil;

/**
 * 全局异常拦截器. <br>
 * 当controller层出现不可预知异常，将由本类统一处理跳转至错误结果页面.
 * <p>
 * Copyright: Copyright (c) 2013-12-2 上午9:34:50
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {
	
	private static final Logger log = Logger.getLogger(ExceptionInterceptor.class);
	
	/**
	 * 方法描述:实现接口方法，统一对异常进行处理并跳转。
	 * @param request 请求
	 * @param response 响应
	 * @param controller 拦截到的controller对象
	 * @param exception 拦截到的异常
	 * @return 视图
	 * date:2013-12-2
	 * add by: liu_tao@xwtec.cn
	 */
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object controller, Exception exception) {
		// 对拦截到的异常进行处理,打印error级别的日志(这样日志处理功能才能发送邮件)
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(exception.toString() + "\n");
		StackTraceElement[] stacks = exception.getStackTrace();
		if(stacks != null) {
			for(StackTraceElement stack : stacks) {
				errorMessage.append("\t" + stack.toString() + "\n");
			}
		}
		log.error(errorMessage.toString());
		
		// 判断是普通请求还是ajax请求，当request.getHeader("x-requested-with")不为空时为ajax请求
		if(request.getHeader("x-requested-with") != null) {
			// ajax请求需要用响应通知页面出现500错误，并将错误信息传给页面
			response.setStatus(Integer.valueOf(ConstantKeys.WebKey.STATUS_500));
            try {
            	response.setHeader("Charset", ConstantKeys.WebKey.CHARACTER_ENCODING_UTF8);
            	response.setContentType(ConstantKeys.WebKey.CONTENT_TYPE_HTML);
            	response.getWriter().print(exception.toString());
            	response.getWriter().flush();
            	response.getWriter().close();
			} catch (IOException e) {
				log.error(CommonUtil.getErrorMessage(Thread.currentThread().getStackTrace()[1], e));
			}
			return null;
		} else {
			// 普通请求需要跳转到错误页面，并将错误信息传给页面
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject(ConstantBusiKeys.ResultInfoKey.MSG, "系统内部错误，查看详细后联系系统管理员!");
			modelAndView.addObject(ConstantBusiKeys.ResultInfoKey.ERROR, exception);
			modelAndView.setViewName(ConstantBusiKeys.ResultInfoKey.RESULT_INFO_PATH);
			return modelAndView;
		}
	}
}
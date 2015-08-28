package com.xwtec.xwserver.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.util.DataSourceContextHolder;

/**
 * 清空线程池中数据的拦截器，
 * 因为Tomcat6下多个request可能共享线程池中线程，数据互相会干扰	，如果不清空，两次请求可能会取到相同的数据.
 * <p>
 * Copyright: Copyright (c) 2014-2-14 下午3:30:03
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class ClearThreadInterceptor implements HandlerInterceptor {

	/**
	 * 方法描述:在DispatcherServlet完全处理完请求后被调用
	 *       当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
	 * @param request 请求
	 * @param response 响应
	 * @param controller 拦截到的controller对象
	 * @param exception 拦截到的异常
	 * date:2014-2-14
	 * add by: liu_tao@xwtec.cn
	 */
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object controller, Exception exception) throws Exception {
	}
	
	/**
	 * 方法描述:在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 * @param request 请求
	 * @param response 响应
	 * @param controller 拦截到的controller对象
	 * @param modelAndView 视图
	 * date:2014-2-14
	 * add by: liu_tao@xwtec.cn
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object controller, ModelAndView modelAndView) throws Exception {		
	}
	
	/**
	 * 方法描述:在业务处理器处理请求之前被调用
	 *       如果返回false,从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 *       如果返回true,执行下一个拦截器,直到所有的拦截器都执行完毕,再执行被拦截的Controller,
	 *       然后进入拦截器链,从最后一个拦截器往回执行所有的postHandle(),接着再从最后一个拦截器往回执行所有的afterCompletion()
	 * @param request 请求
	 * @param response 响应
	 * @param controller 拦截到的controller对象
	 * @return 
	 * date:2014-2-14
	 * add by: liu_tao@xwtec.cn
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object controller) throws Exception {
		DataSourceContextHolder.clear();
		return true;
	}
}

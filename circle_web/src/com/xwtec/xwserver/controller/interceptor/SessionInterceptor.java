package com.xwtec.xwserver.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xwtec.xwserver.constant.ConstantBusiKeys;
import com.xwtec.xwserver.constant.ConstantKeys;

/**
 * Session拦截器. <br>
 * 对除了登录页面、登录提交、验证码以外的controller进行是否登录验证(具体拦截时排除的地址列表参见applicationContext-springmvc.xml)，Session中未取到用户信息的跳转到登录页面.
 * <p>
 * Copyright: Copyright (c) 2013-11-27 上午12:02:53
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author liu_tao@xwtec.cn
 * @version 1.0.0
 */
public class SessionInterceptor implements HandlerInterceptor {
	
	/**
	 * 方法描述:在DispatcherServlet完全处理完请求后被调用
	 *       当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
	 * @param request 请求
	 * @param response 响应
	 * @param controller 拦截到的controller对象
	 * @param exception 拦截到的异常
	 * date:2013-11-27
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
	 * date:2013-11-27
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
	 * date:2013-11-27
	 * add by: liu_tao@xwtec.cn
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object controller) throws Exception {
		// 判断Session是否为空
		if(request.getSession().getAttribute(ConstantBusiKeys.SessionKey.SYS_USER) == null) {
			// 判断是普通请求还是ajax请求，当request.getHeader("x-requested-with")不为空时为ajax请求
			if(request.getHeader("x-requested-with") != null) {
				// 当Session为空时通知前端Session超时
				response.setHeader("Charset", ConstantKeys.WebKey.CHARACTER_ENCODING_UTF8);
            	response.setContentType(ConstantKeys.WebKey.CONTENT_TYPE_HTML);
            	response.getWriter().print(request.getContextPath());
            	response.getWriter().flush();
            	response.getWriter().close();
			} else {
				// 当Session为空时跳转到登出页面
				// 用flag来判断是因为没有Session退出，还是正常单击退出，flag为1时为因为没有Session退出
				request.getRequestDispatcher("/logout/logout.action?flag=1").forward(request,response);
			}
			return false;
		}
		// log4j用的IP
		MDC.put("IP", request.getRemoteAddr());
		return true;
	}
}

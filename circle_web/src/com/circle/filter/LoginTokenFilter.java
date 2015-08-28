package com.circle.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.circle.pojo.user.User;
import com.xwtec.xwserver.constant.ConstantBusiKeys;

public class LoginTokenFilter implements Filter {

	private static final Logger logger = Logger.getLogger(LoginTokenFilter.class.getName());

	private String notFilterUrls = "";

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		logger.info("LoginTokenFilter filter start");
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		HttpSession session = httpReq.getSession();
		String tempStr=httpReq.getRequestURI().replace(httpReq.getContextPath(), "");
		if(tempStr.equals("") || tempStr.equals("/"))
		{
			filterChain.doFilter(httpReq, httpRes);
			return;
		}
		String[] notFilterList = notFilterUrls.split(",");
		for (String notFilter : notFilterList) {
			if (httpReq.getRequestURI().contains(notFilter)) {
				logger.info("httpReq.getRequestURI()=" + httpReq.getRequestURI());
				logger.info("LoginTokenFilter not filter url=" + notFilter);
				filterChain.doFilter(httpReq, httpRes);
				return;
			}
		}
		try {
			// 从session取user
			Object _user = session.getAttribute(ConstantBusiKeys.SessionKey.SYS_USER);
			logger.info("LoginTokenFilter user_name session=" + _user);
			if (_user != null) {
				User user = (User)_user;
				if(user != null){
					filterChain.doFilter(httpReq, httpRes);
					return;
				}
			}else{
				httpRes.getWriter().write("<script>window.parent.location.href='" + httpReq.getContextPath() + "/error/404.htm'</script>");
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("LoginTokenFilter filter error ,errormess=" + e.getMessage());
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		notFilterUrls = filterConfig.getInitParameter("notFilterUrls");
	}
}
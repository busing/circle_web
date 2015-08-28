<%@page import="com.circle.constant.ConstantBusiKeys"%>
<%@page import="com.xwtec.xwserver.util.ProUtil"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% 
	//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	//request.setAttribute("basePath",basePath);
	request.setAttribute("basePath",ProUtil.get(ConstantBusiKeys.PropertiesKey.DOMAIN));
%>

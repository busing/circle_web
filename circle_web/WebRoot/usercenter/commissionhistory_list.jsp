<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://www.xwtech.com/widget" %>
<%@ include file="/common/common.jsp"%> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我的佣金</title>
     <link href="${basePath }/css/all.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/css/base.css" rel="stylesheet" />
    <script type="text/javascript" src="${basePath}/js/common/jquery-1.7.2.min.js" ></script>
    <script type="text/javascript" src="${basePath}/js/common/datepicker/WdatePicker.js"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$(".tabs li").live("click",function(){
    			window.location.href=$(this).attr("link");
    		});
    		
    		$("#search").live("click",function(){
    			$("#historyForm").submit();
    		});
    		
    		var commission_type="${page.searchParam.commission_type}";
    		$("input[name='searchParam[commission_type]'][value='"+commission_type+"']").attr("checked","checked");
    	});
    </script>
</head>
<body>
    <%@ include file="/common/head.jsp"%>  
    <div class="content">
        <div class="center">
        	<div class="ucenter">
	       	<div class="title">
            		查看历史
             </div>
             <%@ include file="/usercenter/menu.jsp" %> 
             <div class="uc_r">
            <div class="yj">
                <div class="mtj">
                	<form id="historyForm" action="${basePath}/commission/queryCommissionHistory.action" method="post" >
                    <ul class="tabs">
                        <li link="${basePath}/usercenter/myCommossion.action">我的佣金</li>
                        <li link="${basePath}/commission/queryCommissionHistory.action" class="selected">查看历史</li>
                    </ul>
                    <div class="condition">
                        <div class="field">
                            <span class="name">类型：</span>
                            <input type="radio" name="searchParam[commission_type]" id="commission_type" value="" checked="checked" /><label for="name1">全部</label>
                            <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                            <input type="radio" name="searchParam[commission_type]" id="commission_type" value="14001,14002"/><label for="name1">推广</label>
                            <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
                            <input type="radio" name="searchParam[commission_type]" id="commission_type" value="14003"/><label for="name2">农场售卖</label>
                        </div>
                        <div class="field">
                            <span class="name">时间：</span>
                            <input type="text" value="${page.searchParam.startDate}" onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})"  name="searchParam[startDate]" />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" value="${page.searchParam.endDate}" onclick="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})"  name="searchParam[endDate]"  />
                            <button id="search"  type="button" class="post">查询</button>
                        </div>
                    </div>

                    <table class="tablist">
                        <thead>
                            <tr>
                                <td>序号</td>
                                <td>来源用户</td>
                                <td>获取佣金(元) </td>
                                <td>行为</td>
                                <td>获得时间</td>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach var="h" items="${history}" varStatus="s">
	                            <tr>
	                                <td class="col1">${s.index+1}</td>
	                                <td class="col2">
	                                   <a href="javaScript:void(0);">${h.name }</a></td>
	                                <td class="col3">${h.commission }</td>
	                                <td class="col4">${h.commission_type_str }</td>
	                                <td class="col5">${h.create_time }</td>
	                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="pager">
                        <!-- 分页控件 -->
                        <div class="pager">
						    <w:turnPage page="${page}"/>
						</div>
						
						<!-- 分页控件 -->
                    </div>
                    </form>
                </div>
            </div>
            </div>
        </div>
    </div>
    </div>
    <%@ include file="/common/foot.jsp"%>  
</body>
</html>
